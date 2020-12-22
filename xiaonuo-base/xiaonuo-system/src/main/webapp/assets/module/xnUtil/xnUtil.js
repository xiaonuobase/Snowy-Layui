/**
 * 小诺工具集
 */
layui.define(['jquery', 'admin', 'formX', 'xmSelect'], function (exports) {
    var $ = layui.$;
    var admin = layui.admin;
    var formX = layui.formX;
    var xmSelect = layui.xmSelect;

    //获取当前登录用户
    var loginUser = layui.data(admin.setter.tableName).loginUser;

    // 权限列表
    var authList = [];

    if(loginUser !== null && loginUser !== undefined ) {
        authList = loginUser.permissions;
    }

    var xnUtil = {

        /**
         * 判断是否有权限
         */
        hasPerm: function (p) {
            if(loginUser !== null && loginUser !== undefined ) {
                if (authList) for (var i = 0; i < authList.length; i++) if (p === authList[i]) return true;
            }
            return false;
        },

        /**
         * 移除没有权限的元素
         */
        renderPerm: function () {
            // 如果loginUser不为空
            if(loginUser !== null && loginUser !== undefined ) {
                if(loginUser.adminType !== 1) {
                    $('[perm-show]').each(function () {
                        if (!xnUtil.hasPerm($(this).attr('perm-show'))) $(this).remove();
                    });
                }
            }
        },

        /**
         * 缓存字典信息
         */
        cacheDictData: function () {
            // 获取字典数据
            admin.req('sysDictType/tree', function(res){
                // 并存储
                layui.data(admin.setter.tableName, {key: 'dictData', value: res.data});
            });
        },

        /**
         * 渲染字典信息
         *
         * 字典值编码，字典类型编码
         */
        rendDataTableDict: function (dictCode, dictTypeCode) {
            var result = '未知';
            var dictData = layui.data(admin.setter.tableName).dictData;
            if(dictData === null || dictData === undefined) {
                return result;
            }
            dictData.forEach(function (item) {
                if(dictTypeCode.toString() === item.code) {
                    item.children.forEach(function (childrenItem) {
                        if(dictCode.toString() === childrenItem.code) {
                            result = childrenItem.name;
                        }
                    });
                }
            });
            return result;
        },

        /**
         * 根据字典类型编码获取子项
         */
        getDictDataByDictTypeCode: function (dictTypeCode, exclude) {
            var result = [];
            var dictData = layui.data(admin.setter.tableName).dictData;
            if(dictData === null || dictData === undefined) {
                return result;
            }
            dictData.forEach(function (item) {
                if(dictTypeCode.toString() === item.code) {
                    if(exclude !== null && exclude !== undefined && exclude.length !== 0) {
                        for(var i=0; i<item.children.length; i++) {
                            var indexOf = exclude.indexOf(item.children[i].name);
                            if(indexOf !== -1) {
                                item.children.splice(i, 1);
                            }
                        }
                    }
                    result = item.children;
                }
            });
            return result;
        },

        /**
         * 渲染字典下拉
         *
         * 表单filter名，字段名，字典类型编码，占位文字，需排除文字
         */
        rendDictDropDown: function (filterName, fieldName, dictTypeCode, placeholderName, exclude) {
            var elem;
            if(filterName !== null && filterName !== undefined) {
                elem = '[lay-filter="'+ filterName + '"] select[name="' + fieldName + '"]';
            } else {
                elem = 'select[name="' + fieldName + '"]';
            }
            return formX.renderSelect({
                elem: elem,
                data: xnUtil.getDictDataByDictTypeCode(dictTypeCode, exclude),
                name: 'name',
                value: 'code',
                hint: placeholderName
            });
        },

        /**
         * 渲染远程数据下拉
         *
         * 元素id，字段名，远程地址，是否单选，占位文字
         */
        rendRemoteDataDropDown: function (elem, fieldName, name, value, remoteUrl, isSingleSelect, placeholderName) {
            var remoteDataRenderIns = {};
            admin.req(remoteUrl, function(res){
                remoteDataRenderIns = xmSelect.render({
                    el: elem,
                    name: fieldName,
                    data: res.data,
                    layVerify: 'required',
                    layVerType: 'tips',
                    radio: isSingleSelect,
                    prop: {
                        name: name,
                        value: value
                    },
                    hint: placeholderName
                });
            }, {async: false});
            return remoteDataRenderIns;
        },

        /**
         * 表格渲染之后的回调
         */
        tableDone: function(insTb, res, curr, count) {
            if(res.data.length === 0 && count !== 0) {
                if(curr !== 1) {
                    insTb.reload({page: {curr: curr - 1}});
                } else {
                    insTb.reload({page: {curr: 1}});
                }
            }
            xnUtil.renderPerm();
        },

        /**
         * 递归处理没有子节点的ztree数据
         */
        handleNoChildrenZtreeData: function (data) {
            data.forEach(function (value) {
                if(value.children.length === 0) {
                    delete value.children;
                } else {
                    xnUtil.handleNoChildrenZtreeData(value.children);
                }
            })
            return data;
        },

        /**
         * 递归处理将ztree数据展开，spread方式
         */
        handleZtreeDataSpread: function (data) {
            data.forEach(function (value) {
                if(value.children.length !== 0) {
                    value.spread = true;
                    xnUtil.handleZtreeDataSpread(value.children);
                }
            })
            return data;
        },

        /**
         * 递归处理将ztree数据展开，open方式
         */
        handleZtreeDataOpen: function (data) {
            data.forEach(function (value) {
                if(value.children.length !== 0) {
                    value.open = true;
                    xnUtil.handleZtreeDataOpen(value.children);
                }
            })
            return data;
        },

        /**
         * 构造树顶级节点
         */
        handleZtreeDataRootNode: function (data) {
            var resultData = [];
            var rootData = {};
            rootData.id = 0;
            rootData.parentId = -1;
            rootData.title = '顶级';
            rootData.value = '0';
            rootData.weight = 100;
            rootData.children = data;
            resultData.push(rootData);
            return resultData;
        }
    };

    exports('xnUtil', xnUtil);
});