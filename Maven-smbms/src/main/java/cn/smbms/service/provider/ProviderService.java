package cn.smbms.service.provider;


import cn.smbms.pojo.Provider;

import java.util.List;

public interface ProviderService {
    /**
     * 根据条件查询供应商列表
     * @param proCode
     * @param proName
     * @param currentPageNo
     * @param pageSize
     * @return
     */
    public List<Provider> getProList(String proCode, String proName, int currentPageNo, int pageSize);

    /**
     * 供应商用户记录数
     * @return
     */
    public int proCount(String proCode, String proName);


    public List<Provider> list();

    public int addProvide(Provider provider);

    public Provider ProView(String id);

    public int modifyPro(Provider provider);
}
