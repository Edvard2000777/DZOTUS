import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

public class HelloOtus {
    public static void main(String[] args) {
        String tmpValue = "a_b_c_1_2_3";
        String[] valArr = tmpValue.split("_");

        // Находим подстроки массива строк и объединяем их в конце
        String tmpVal = "";
        for (int i = 1; i < valArr.length; i++) {
            tmpVal = tmpVal.equalsIgnoreCase("") ? valArr[i] : tmpVal + "_" + valArr[i];
        }
        System.out.println(tmpVal);
        System.out.println("———————");

        // Вышеуказанный абзац эквивалентен следующему предложению
        System.out.println(Joiner.on("_").join(Lists.newArrayList(valArr).subList(1, valArr.length)));

    }
}