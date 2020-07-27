package ${basePackage}.controller;
import ${baseResult};
import ${baseResultGenerator};
import ${basePackage}.entity.${modelNameUpperCamel};
import ${basePackage}.service.${modelNameUpperCamel}Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;
import javax.annotation.Resource;
import java.util.List;
import io.swagger.annotations.*;



/**
* Created by ${author} on ${date}.
*/
@Api(value = "${businessName}", tags = {"${businessName}"})
@RestController
@RequestMapping("${baseRequestMapping}")
public class ${modelNameUpperCamel}Controller{
    @Resource
    private ${modelNameUpperCamel}Service ${modelNameLowerCamel}Service;

    @ApiOperation(value = "${businessName}添加", tags = {"${businessName}"}, notes = "${businessName}添加")
    @PostMapping(value="/add",name="${businessName}添加")
    public Result add(@ApiParam ${modelNameUpperCamel} ${modelNameLowerCamel}) {
        ${modelNameLowerCamel}Service.save(${modelNameLowerCamel});
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "${businessName}删除", tags = {"${businessName}"}, notes = "${businessName}删除")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id",required=true, value = "${businessName}id", dataType = "Long", paramType = "query")
    })
    @PostMapping(value="/delete",name="${businessName}删除")
    public Result delete(@RequestParam Long id) {
        ${modelNameLowerCamel}Service.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "${businessName}修改", tags = {"${businessName}"}, notes = "${businessName}修改,对象主键必填")
    @PostMapping(value="/update",name="${businessName}修改")
    public Result update(@ApiParam ${modelNameUpperCamel} ${modelNameLowerCamel}) {
        ${modelNameLowerCamel}Service.update(${modelNameLowerCamel});
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "${businessName}详细信息", tags = {"${businessName}"}, notes = "${businessName}详细信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id",required=true, value = "${businessName}id", dataType = "Long", paramType = "query")
    })
    @PostMapping(value="/detail",name="${businessName}详细信息")
    public Result detail(@RequestParam Integer id) {
        ${modelNameUpperCamel} ${modelNameLowerCamel} = ${modelNameLowerCamel}Service.findById(id);
        return ResultGenerator.genSuccessResult(${modelNameLowerCamel});
    }

    @ApiOperation(value = "${businessName}列表信息", tags = {"${businessName}"}, notes = "${businessName}列表信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "search", value = "查询条件json", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "order", value = "排序json", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "page", value = "页码", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "size", value = "每页显示的条数", dataType = "String", paramType = "query", defaultValue = "10")
    })
    @PostMapping(value = "/list", name = "${businessName}列表信息")
    public Result list(@RequestParam(defaultValue = "{}") String search,
                       @RequestParam(defaultValue = "{}") String order,
                       @RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "10") Integer size) {
        return ${modelNameLowerCamel}Service.list(search, order, page, size);
    }
}
