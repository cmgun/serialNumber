import com.cmgun.common.utils.UUIDGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 测试类
 *
 * @author cmgun
 * @since 2017/6/28
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MyTest {

    @Test
    public void generateID() {
        // 返回18位的流水号
        String id1 = UUIDGenerator.generate();
        // 特定前缀
        String id2 = UUIDGenerator.generate("test");
        String id3 = UUIDGenerator.generate("tttt", 8, 10, 2);
    }
}