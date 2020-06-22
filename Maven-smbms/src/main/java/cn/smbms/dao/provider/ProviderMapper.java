package cn.smbms.dao.provider;

import cn.smbms.pojo.Provider;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProviderMapper {
  /**
     * 供应商列表
     * @param proCode
     * @param proName
     * @param currentPageNo
     * @param pageSize
     * @return
     */
    public List<Provider> proList(@Param("proCode") String proCode,
                                  @Param("proName") String proName,
                                  @Param("currentPageNo") int currentPageNo,
                                  @Param("pageSize") int pageSize);

    /**
     * 查询供应商列表记录数
     * @return
     */
    public int proCount(@Param("proCode") String proCode, @Param("proName") String proName);


    public List<Provider> list();

    public int addProvide(Provider provider);

    public Provider ProView(@Param("id") String id);

    public int modifyPro(Provider provider);

}
