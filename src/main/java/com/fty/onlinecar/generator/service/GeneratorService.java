package com.fty.onlinecar.generator.service;

import com.fty.onlinecar.base.service.AbstractService;
import com.fty.onlinecar.response.Result;
import com.fty.onlinecar.response.ResultEnum;
import com.fty.onlinecar.response.ResultGenerator;
import com.fty.onlinecar.utils.ServiceException;
import com.google.common.base.CaseFormat;
import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.fty.onlinecar.generator.config.ProjectConstant.*;

@Service
public class GeneratorService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String JAVA_PATH = "/src/main/java"; //java文件路径

    private static final String AUTHOR = "wanghuiwen";//@author

    @Autowired
    DataSourceProperties dataSourceProperties;

    /**
     * @param isMapper     是否生成mapper
     * @param isService    是否生产service
     * @param businessName 业务名称
     * @param tableNames   数据表名称
     * @param moduleName   maven模块名称
     * @param isModel      是否生成 model
     * @param isController 是否生产controller
     */
    public Result genCode(boolean isModel, boolean isMapper, boolean isService, boolean isController, String businessName, String tableNames, String moduleName) {
        File path = new File(System.getProperty("user.dir") + File.separator + moduleName);
        if (!path.exists()) {
            return ResultGenerator.genResult(ResultEnum.MODULE_EXISTS);
        }
        genCodeByCustomModelName(tableNames, null, businessName, isService, isMapper, isController, isModel, path.getPath(), moduleName);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * @param tableName 数据表名称
     * @param modelName 自定义的 Model 名称
     */
    public void genCodeByCustomModelName(String tableName, String modelName, String businessName, boolean isServer, boolean isMapper, boolean isController, boolean isModel, String modulePath, String moduleName) {
        String modelPackage = moduleName.replaceAll("-", ".");
        if (isModel) {
            genModelAndMapper(tableName, modelName, isMapper, modulePath, modelPackage);
        }
        if (isServer) {
            genService(tableName, modelName, modulePath, modelPackage);
        }
        if (isController) {
            genController(tableName, modelName, businessName, modulePath, modelPackage);
        }

    }


    public void genModelAndMapper(String tableName, String modelName, boolean isMapper, String modulePath, String modelPackage) {
        Context context = new Context(ModelType.FLAT);


        PluginConfiguration p = new PluginConfiguration();
        p.setConfigurationType("org.mybatis.generator.plugins.SerializablePlugin");

        context.addPluginConfiguration(p);

        PluginConfiguration p2 = new PluginConfiguration();
        p2.setConfigurationType("com.fty.onlinecar.generator.config.NameSpace");

        context.addPluginConfiguration(p2);

        PluginConfiguration mapperAnnotationPlugin = new PluginConfiguration();
        mapperAnnotationPlugin.setConfigurationType("org.mybatis.generator.plugins.MapperAnnotationPlugin");
        context.addPluginConfiguration(mapperAnnotationPlugin);

        context.setId("Potato");
        context.setTargetRuntime("MyBatis3Simple");
        context.addProperty(PropertyRegistry.CONTEXT_BEGINNING_DELIMITER, "`");
        context.addProperty(PropertyRegistry.CONTEXT_ENDING_DELIMITER, "`");

        JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
        jdbcConnectionConfiguration.setConnectionURL(dataSourceProperties.getUrl());
        jdbcConnectionConfiguration.setUserId(dataSourceProperties.getUsername());
        jdbcConnectionConfiguration.setPassword(dataSourceProperties.getPassword());
        jdbcConnectionConfiguration.setDriverClass(dataSourceProperties.getDriverClassName());
        jdbcConnectionConfiguration.addProperty("nullCatalogMeansCurrent","true");
        context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);

        PluginConfiguration pluginConfiguration = new PluginConfiguration();
        pluginConfiguration.setConfigurationType("tk.mybatis.mapper.generator.MapperPlugin");
        pluginConfiguration.addProperty("mappers", MAPPER_INTERFACE_REFERENCE);
        context.addPluginConfiguration(pluginConfiguration);

        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
        javaModelGeneratorConfiguration.setTargetProject(modulePath + JAVA_PATH);
        javaModelGeneratorConfiguration.setTargetPackage(BASE_PACKAGE + modelPackage + MODEL_PACKAGE);
        context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);

        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
        sqlMapGeneratorConfiguration.setTargetProject(modulePath + JAVA_PATH);
        sqlMapGeneratorConfiguration.setTargetPackage(BASE_PACKAGE + modelPackage + MAPPER_PACKAGE + ".mapper");
        context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);


        JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
        javaClientGeneratorConfiguration.setTargetProject(modulePath + JAVA_PATH);
        javaClientGeneratorConfiguration.setTargetPackage(BASE_PACKAGE + modelPackage + MAPPER_PACKAGE);
        javaClientGeneratorConfiguration.setConfigurationType("XMLMAPPER");
        if (isMapper) {
            context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);
            logger.info(tableName + "Mapper.java 生成成功");
        }
        TableConfiguration tableConfiguration = new TableConfiguration(context);
        tableConfiguration.setTableName(tableName);
        if (StringUtils.isNotEmpty(modelName)) tableConfiguration.setDomainObjectName(modelName);
        tableConfiguration.setGeneratedKey(new GeneratedKey("id", "Mysql", true, null));
        context.addTableConfiguration(tableConfiguration);

        List<String> warnings;
        MyBatisGenerator generator;
        try {
            Configuration config = new Configuration();
            config.addContext(context);
            config.validate();

            boolean overwrite = true;
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            warnings = new ArrayList<String>();
            generator = new MyBatisGenerator(config, callback, warnings);
            generator.generate(null);
        } catch (Exception e) {
            logger.error("生成Model和Mapper失败", e);
            throw new RuntimeException("生成Model和Mapper失败", e);
        }

        if (generator.getGeneratedJavaFiles().isEmpty() || generator.getGeneratedXmlFiles().isEmpty()) {
            logger.error("生成Model和Mapper失败");
            throw new ServiceException("生成失败：" + warnings);
        }
        logger.info("生成Model和Mapper成功");
    }

    public void genService(String tableName, String modelName, String modulePath,String modelPackage) {
        try {
            freemarker.template.Configuration cfg = getConfiguration();

            Map<String, Object> data = new HashMap<>();
            data.put("date", new SimpleDateFormat("yyyy/MM/dd").format(new Date()));
            data.put("author", AUTHOR);
            String modelNameUpperCamel = StringUtils.isEmpty(modelName) ? tableNameConvertUpperCamel(tableName) : modelName;
            data.put("modelNameUpperCamel", modelNameUpperCamel);
            data.put("modelNameLowerCamel", tableNameConvertLowerCamel(tableName));
            data.put("basePackage", BASE_PACKAGE + modelPackage);
            logger.info(com.fty.onlinecar.base.service.Service.class.getName());
            data.put("baseService", com.fty.onlinecar.base.service.Service.class.getName());
            data.put("baseServiceImpl", AbstractService.class.getName());

            File file = new File(modulePath + JAVA_PATH + packageConvertPath(BASE_PACKAGE + modelPackage+SERVICE_PACKAGE) + modelNameUpperCamel + "Service.java");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            cfg.getTemplate("service.ftl").process(data,
                    new FileWriter(file));
            logger.info(modelNameUpperCamel + "Service.java 生成成功");

            File file1 = new File(modulePath + JAVA_PATH + packageConvertPath(BASE_PACKAGE + modelPackage+SERVICE_IMPL_PACKAGE) + modelNameUpperCamel + "ServiceImpl.java");
            if (!file1.getParentFile().exists()) {
                file1.getParentFile().mkdirs();
            }
            cfg.getTemplate("service-impl.ftl").process(data,
                    new FileWriter(file1));
            logger.info(modelNameUpperCamel + "ServiceImpl.java 生成成功");
        } catch (Exception e) {
            throw new ServiceException("生成Service失败", e);
        }
    }

    public void genController(String tableName, String modelName, String businessName, String modulePath,String modelPackage) {
        try {
            freemarker.template.Configuration cfg = getConfiguration();

            Map<String, Object> data = new HashMap<>();
            data.put("date", new SimpleDateFormat("yyyy/MM/dd").format(new Date()));
            data.put("author", AUTHOR);
            String modelNameUpperCamel = StringUtils.isEmpty(modelName) ? tableNameConvertUpperCamel(tableName) : modelName;
            data.put("baseRequestMapping", modelNameConvertMappingPath(modelNameUpperCamel));
            data.put("modelNameUpperCamel", modelNameUpperCamel);
            data.put("modelNameLowerCamel", CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, modelNameUpperCamel));
            data.put("basePackage", BASE_PACKAGE + modelPackage);
//            data.put("baseController", Ctrl.class.getName());
            data.put("baseResult", Result.class.getName());
            data.put("baseResultGenerator", ResultGenerator.class.getName());
            data.put("businessName", businessName);

            File file = new File(modulePath + JAVA_PATH + packageConvertPath(BASE_PACKAGE + modelPackage+CONTROLLER_PACKAGE) + modelNameUpperCamel + "Controller.java");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            //cfg.getTemplate("controller-restful.ftl").process(data, new FileWriter(file));
            cfg.getTemplate("controller.ftl").process(data, new FileWriter(file));

            logger.info(modelNameUpperCamel + "Controller.java 生成成功");
        } catch (Exception e) {
            throw new RuntimeException("生成Controller失败", e);
        }

    }

    private freemarker.template.Configuration getConfiguration() throws IOException {
        freemarker.template.Configuration cfg = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_23);
        logger.info(new File(this.getClass().getResource("").getPath()).getParent());
        cfg.setDirectoryForTemplateLoading(new File(new File(this.getClass().getResource("").getPath()).getParent()+File.separator+"template"));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        return cfg;
    }

    private static String tableNameConvertLowerCamel(String tableName) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tableName.toLowerCase());
    }

    private static String tableNameConvertUpperCamel(String tableName) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName.toLowerCase());

    }

    private static String tableNameConvertMappingPath(String tableName) {
        tableName = tableName.toLowerCase();//兼容使用大写的表名
        return "/" + (tableName.contains("_") ? tableName.replaceAll("_", "/") : tableName);
    }

    private static String modelNameConvertMappingPath(String modelName) {
        String tableName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, modelName);
        return tableNameConvertMappingPath(tableName);
    }

    private static String packageConvertPath(String packageName) {
        return String.format("/%s/", packageName.contains(".") ? packageName.replaceAll("\\.", "/") : packageName);
    }
}
