package ${basePackage}.service;
import ${basePackage}.entity.${modelNameUpperCamel};
import ${baseService};
import com.fty.onlinecar.response.Result;

/**
 * Created by ${author} on ${date}.
 */
public interface ${modelNameUpperCamel}Service extends Service<${modelNameUpperCamel}> {
   Result list(String search, String order, Integer page, Integer size);
}
