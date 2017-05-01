import com.yonyou.yuncai.cpu.bi.utils.database.TempTable;
import com.yonyou.yuncai.cpu.bi.utils.database.TempTableTool;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.sql.SQLException;

/**
 * Created by fengjqc on 2017/3/20.
 */
public class DataBaseTest  extends AbstractJUnit4SpringContextTests{
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    TempTableTool tempTableTool;
//    @Test
//    public void tempTableTest(){
//        try {
//            tempTableTool.createTempTable();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

//    @Test
//    public void testGetPrice(){
//        String json ="[{\"id\":\"1430\",\"supplierId\":\"1001E410000000003YYJ\",\"materialId\":\"1001E410000000003NJY\",\"purchaserId\":\"30\"},{\"id\":\"1431\",\"supplierId\":\"1001E410000000003YYJ\",\"materialId\":\"1001E410000000003NJY\",\"purchaserId\":\"30\"}]";
//        iBiPriceService.getPrice(json);
//    }

//    @Resource
//    private IUsersService usersService;
//
//    @Test
//    public void testGetMerchant() {
//        Users users = usersService.getUserById(10);
//        if(users!=null){
//            System.out.println(users.getUserId());
//        }else{
//            System.out.println("没有查找到数据！");
//        }
//    }
}
