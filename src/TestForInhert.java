public class TestForInhert {
    public static void main(String[] args){
        son son = new son();
        son.go();
    }
}
class father{
    String string = "father";

    public void go(){
        print(string);
    }

    public void print(String string){
        System.out.println(string);
    }
}

class son extends father{
    String string = "son";

//    public void go(){
//        print(string);
//    }
    public void print(String string){
        System.out.println(string+"son");
    }
}