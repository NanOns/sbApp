import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Author: zhshuo
 * Time : 2018/10/18 10:16 星期四
 **/
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String name;

    private int age;

    private String mark;

    public static boolean randomTest(User user){
        return user.getName().contains("zh");
    }

    public static boolean ageCondition(User user){
        return user.getAge() > 20;
    }



}
