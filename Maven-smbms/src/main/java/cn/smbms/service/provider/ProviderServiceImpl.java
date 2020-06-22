package cn.smbms.service.provider;

import cn.smbms.dao.provider.ProviderMapper;
import cn.smbms.pojo.Provider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
@Transactional
@Service("providerService")
public class ProviderServiceImpl implements ProviderService{

    @Resource
    private ProviderMapper providerMapper;

    @Override
    public List<Provider> getProList(String proCode, String proName, int currentPageNo, int pageSize) {
        int beginIndex = (currentPageNo-1)*pageSize;
        return providerMapper.proList(proCode,proName,beginIndex,pageSize);
    }

    @Override
    public int proCount(String proCode,String proName) {
        return providerMapper.proCount(proCode,proName);
    }

    @Override
    public List<Provider> list() {
        return providerMapper.list();
    }

    @Override
    public int addProvide(Provider provider) {
        return providerMapper.addProvide(provider);
    }

    @Override
    public Provider ProView(String id) {
        return providerMapper.ProView(id);
    }

    @Override
    public int modifyPro(Provider provider) {
        return providerMapper.modifyPro(provider);
    }
}
