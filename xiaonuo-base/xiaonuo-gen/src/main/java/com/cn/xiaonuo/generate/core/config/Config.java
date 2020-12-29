package com.cn.xiaonuo.generate.core.config;

import java.io.File;

/**
 * 代码生成配置
 *
 * @author yubaoshan
 * @date 2020-12-19 02:30:56
 */
public class Config {

    /**
     * 路径分离（不通的机器，取不同的路径）
     */
    public static String FILE_SEP = File.separator;

    /**
     * 存放vm模板位置
     */
    public static String templatePath = "template" + FILE_SEP;

    /**
     * 主键标识
     */
    public static String DB_TABLE_COM_KRY = "PRI";

    /**
     * 模块名（一般为modular，无特殊要求一般不改）
     */
    public static String MODULAR_NAME = "modular";

    /**
     * 本项目生成时是否覆盖
     */
    public static final boolean FLAG = false;

    /**
     * 大模块名称（生成到代码中哪个模块下）
     */
    public static String BASE_MODULAR_NAME = "xiaonuo-main";

    /**
     * java文件夹
     */
    public static String BASE_JAVA_PAHT = FILE_SEP + "src" + FILE_SEP + "main" + FILE_SEP + "java" + FILE_SEP;

    /**
     * html文件夹
     */
    public static String BASE_HTML_PAHT = FILE_SEP + "src" + FILE_SEP + "main" + FILE_SEP + "webapp" + FILE_SEP + "templates" + FILE_SEP;

    /**
     * 代码生成路径
     */
    public static String controllerPath;
    public static String entityPath;
    public static String enumsPath;
    public static String mapperPath;
    public static String mappingPath;
    public static String paramPath;
    public static String servicePath;
    public static String serviceImplPath;
    public static String htmlPath;
    public static String htmlFormPath;

    /**
     * 各个代码存放路径文件夹
     */
    public static String[] xnCodeGenFilePath (String busName, String packageName) {
        String packageNameString = packageName.replace(".", FILE_SEP) + FILE_SEP;
        controllerPath = BASE_JAVA_PAHT + packageNameString + MODULAR_NAME + FILE_SEP + busName + FILE_SEP + "controller" + FILE_SEP;
        entityPath = BASE_JAVA_PAHT + packageNameString + MODULAR_NAME + FILE_SEP + busName + FILE_SEP + "entity" + FILE_SEP;
        enumsPath = BASE_JAVA_PAHT+ packageNameString  + MODULAR_NAME + FILE_SEP + busName + FILE_SEP + "enums" + FILE_SEP;
        mapperPath = BASE_JAVA_PAHT + packageNameString  + MODULAR_NAME + FILE_SEP + busName + FILE_SEP + "mapper" + FILE_SEP;
        mappingPath = mapperPath + FILE_SEP + "mapping" + FILE_SEP;
        paramPath = BASE_JAVA_PAHT+ FILE_SEP + packageNameString  + MODULAR_NAME + FILE_SEP + busName + FILE_SEP + "param" + FILE_SEP;
        servicePath = BASE_JAVA_PAHT+ FILE_SEP + packageNameString  + MODULAR_NAME + FILE_SEP + busName + FILE_SEP + "service" + FILE_SEP;
        serviceImplPath = servicePath + FILE_SEP + "impl" + FILE_SEP;
        htmlPath = BASE_HTML_PAHT + FILE_SEP + busName + FILE_SEP;
        htmlFormPath = BASE_HTML_PAHT + FILE_SEP + busName + FILE_SEP;
        return new String[] {
                controllerPath, entityPath, enumsPath, mapperPath, mappingPath, paramPath, servicePath, serviceImplPath, htmlPath, htmlFormPath
        };
    }

    /**
     * 模板文件
     */
    public static String[] xnCodeGenTempFile = {
            "Controller.java.vm",
            "entity.java.vm",
            "ExceptionEnum.java.vm",
            "Mapper.java.vm",
            "Mapper.xml.vm",
            "Param.java.vm",
            "Service.java.vm",
            "ServiceImpl.java.vm",
            "index.html.vm",
            "form.html.vm"
    };

    /**
     * 本地项目根目录
     */
    public static String getLocalPath () {
        return System.getProperty("user.dir") + FILE_SEP + BASE_MODULAR_NAME + FILE_SEP;
    }

}
