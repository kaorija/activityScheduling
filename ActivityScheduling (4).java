import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ActivityScheduling {

    public static void main(String[] args){
        if(args.length < 2){
            System.out.println("Invalid commandline arguments!");
            System.exit(1);
        }

        String infileName = args[0];
        String outfileName = args[1];

        // create out file
        File outfile = new File(outfileName);
        try {
            if(!outfile.createNewFile()){
                outfile.delete();
                outfile.createNewFile();
            }
        }catch (IOException ex){
            System.out.println("Error: " + ex.getMessage());
        }

        //read input file
        File infile = new File(infileName);
        Scanner fileReader;
        FileWriter fileWriter;
        try{
            fileReader = new Scanner(infile);
            fileWriter = new FileWriter(outfileName);
            int instanceCount = 0, activityCount = 0;
            if(fileReader.hasNextLine()){
                instanceCount = Integer.parseInt(fileReader.nextLine());
            }
            for(int i=0; i< instanceCount; i++){
                if(fileReader.hasNextLine()){
                    activityCount = Integer.parseInt(fileReader.nextLine());
                    ArrayList<Integer> sList = new ArrayList<>();
                    ArrayList<Integer> fList = new ArrayList<>();
                    ArrayList<Integer> actList = new ArrayList<>();
                    sList.add(-1); fList.add(0);
                    String line;
                    for(int j=0; j< activityCount; j++){
                        line = fileReader.nextLine();
                        String[] vals = line.split(" ");
                        int sj = Integer.parseInt(vals[0]);
                        int fj = Integer.parseInt(vals[1]);
                        sList.add(sj);
                        fList.add(fj);
                    }

                    maxActivities(sList, fList, activityCount, actList);

//                    activitySelector(sList, fList, 0, activityCount, actList);
                    fileWriter.write(String.valueOf(actList.size() - 1) + "\n");
                }
            }
            fileWriter.close();
        }catch (FileNotFoundException ex){
            System.out.println("Error: invalid input file name.");
            System.exit(1);
        }catch (NumberFormatException ex){
            System.out.println("Error: the input file contains invalid value.");
            System.exit(1);
        }catch (IOException ex){
            System.out.println("Error: invalid out file name.");
            System.exit(1);
        }
    }

    public static void maxActivities(ArrayList<Integer> s, ArrayList<Integer> f, int n, ArrayList<Integer> actlist)
    {
        //sort finishing times
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (f.get(j) < f.get(minIndex)) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                Integer temp = f.get(i);
                f.set(i, f.get(minIndex));
                f.set(minIndex, temp);

                Integer temp1 = s.get(i);
                s.set(i, s.get(minIndex));
                s.set(minIndex, temp1);
            }
        }


        int i,j;
        //first is selected as it is sorted according to time
        i=0;
        actlist.add(i);
        //consider remaining
        for( j=1;j<n;j++)
        {
            //if this activity starting time is greaterthan
            //or equal to finshing time of previous activity,
            //than select it.
            if(s.get(j)>=f.get(i))
            {
                actlist.add(j);
                i=j;
            }
        }
    }
}
