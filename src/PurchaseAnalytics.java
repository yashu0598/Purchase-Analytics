import java.util.*;
import java.io.*;
import java.text.DecimalFormat;
class PurchaseAnalytics{
    static Map<Integer,Integer> product_list;
    static Map<Integer,List<Integer>> result;
    public static void read_products(String file_name) throws Exception{
        BufferedReader br=new BufferedReader(new FileReader(new File(file_name)));
        String line = br.readLine();
        while((line=br.readLine())!=null){
            String[] temp=line.split(",");
            product_list.put(Integer.parseInt(temp[0]),Integer.parseInt(temp[3]));
        }
        br.close();
    }

    public static void main(String args[]) throws Exception{
        product_list=new HashMap<>();
        result=new TreeMap<>();
        read_products(args[1]);
        BufferedReader br=new BufferedReader(new FileReader(new File(args[0])));
        String line = br.readLine();
        while((line=br.readLine())!=null){
            String[] temp=line.split(",");
            int dept_id=product_list.get(Integer.parseInt(temp[1]));
            List<Integer> a=new ArrayList<>();
            if(result.containsKey(dept_id)){
                List<Integer> o=result.get(dept_id);
                a.add(o.get(0)+1);
                a.add(o.get(1)+(Integer.parseInt(temp[3])==1?0:1));
            }else{
                a.add(1);
                a.add((Integer.parseInt(temp[3])==1?0:1));
            }
            result.put(dept_id,a);
        }
        br.close();
        DecimalFormat f = new DecimalFormat("0.00");
        String s="department_id,number_of_orders,number_of_first_orders,percentage";
        for(Map.Entry<Integer,List<Integer>> m:result.entrySet()){
            int a=m.getValue().get(0),b=m.getValue().get(1);
            s=s+"\n"+m.getKey()+","+a+","+b+","+f.format(b/(double)a);
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(args[2]));
		writer.write(s);
		writer.close();
    }
}
