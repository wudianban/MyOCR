package com.cdsmartcity.baselibrary.utilTooth.permissions;

import java.util.List;

/**
 *
 *
 * @author Li
 * 创建日期：2021/9/1 10:04
 * 描述：IPermissionsResult
 */
public interface IPermissionsResult {
    void passPermissons();

    void forbitPermissons(List<String> permissions);
}
