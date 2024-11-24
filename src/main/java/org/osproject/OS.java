package org.osproject;

import java.io.*;

public class OS {
    /** Storage stuff */
    private char [][]memory= new char[100][4];
    private char []buffer= new char[40];
    private char []R=new char[4];
    private char []IR=new char[4];

    /** Counters and trackers */
    private int memory_used;
    private int IC;
    private int T;
    private int SI;

    /** For File Handling */
    private String input_file;
    private String output_file;
    private  FileReader input;
    private BufferedReader fread;
    private  FileWriter output;

    /** Constructor to initialize object data */
    public OS(String inputFile, String outputFile) {
        this.input_file = inputFile;
        this.SI = 0;
        try {
            File inputFileObj = new File(inputFile);
            if (!inputFileObj.exists()) {
                throw new FileNotFoundException("Input file not found: " + inputFile);
            }
            this.input = new FileReader(inputFileObj);
            this.fread = new BufferedReader(input);
            this.output = new FileWriter(outputFile);
        } catch (IOException e) {
            System.err.println("Error setting up files: " + e.getMessage());
            e.printStackTrace();
        }
    }


    /** LOAD Method */
    public void LOAD()
    {
        String line;
        try {

            while((line=fread.readLine()) != null)
            {
                buffer=line.toCharArray();
                if(buffer[0]=='$'&& buffer[1]=='A'&&buffer[2]=='M'&& buffer[3]=='J') {
                    /**
                     * Post program-card algorithm
                     * ============================
                     * 1. Reset all storage stuff, trackers and counters
                     * 2. Store program commands into memory array
                     *
                     * */
                    System.out.println("Program card detected");
                    init();
                    continue;
                }
                else if(buffer[0]=='$'&& buffer[1]=='D'&&buffer[2]=='T'&& buffer[3]=='A')
                {
                    /**
                     * Post data-card algorithm
                     * ============================
                     * 1. start execution of program in slave mode
                     *
                     * */
                    System.out.println("Data card detected");
                    execute();
                    continue;
                }
                else if(buffer[0]=='$'&& buffer[1]=='E'&&buffer[2]=='N'&& buffer[3]=='D')
                {
                    System.out.println("END card detected");
                    output.write("\n\n\n");
                    print_memory();
                    break;
                }
                if(memory_used==100)
                {   //abort;
                    System.out.println("Abort due to exceed memory usage");
                }

                System.out.println("ur program starts here");
                for (int i = 0; i < line.length();) {
                    memory[memory_used][i%4]=buffer[i];
                    i++;
                    if(i%4==0)
                        memory_used++;
                }


            }
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(memory_used);
        //print_memory();
    }
    private void execute(){
        /**
         * Execute User Program Algorithm
         * ============================
         * 1. Load instruction from memory[IC] to IR
         * 2. Increment IC once instruction is loaded
         * 3. Examine loaded instruction
         *  If its
         *
         *      LR
         *      ===
         *      - Convert instruction in string and slice string from index
         *        2 to fetch the address. Convert this address to integer and store it in num.
         *      - Now load instruction available at this memory address into R[] array. (e.g R = memory[num])
         *
         *      SR
         *      ===
         *      - Convert instruction in string and slice string from index
         *        2 to fetch the address. Convert this address to integer and store it in num.
         *      - Now load instruction available at this R into memory at specified address. (e.g memory[num] = R)
         *
         *      CR
         *      ===
         *      - Convert instruction in string and slice string from index
         *        2 to fetch the address. Convert this address to integer and store it in num.
         *      - Compare data available at specified memory address and R. If its same then set T as 1, otherwise set T as 0.
         *
         *      BT
         *      ===
         *      - If T is 1, then Convert instruction in string and slice string from index 2 to fetch the address. Convert this address to integer and store it in num.
         *      - Set IC = num
         *      - Set T = 0
         *
         *      GD
         *      ===
         *      - Set SI = 1
         *      - Call master mode
         *
         *      PD
         *      ===
         *      - Set SI = 2
         *      - Call master mode
         *
         *      H
         *      ===
         *      - Set SI = 3
         *      - Call master mode
         * */

        while(1<2)
        {
            if(IC==100)
                break;
            IR[0]=memory[IC][0];
            IR[1]=memory[IC][1];
            IR[2]=memory[IC][2];
            IR[3]=memory[IC][3];

            IC++;
            if(IR[0]=='L' && IR[1]=='R')
            {
                String line = new String(IR);
                int num=Integer.parseInt(line.substring(2));
                R[0]=memory[num][0];
                R[1]=memory[num][1];
                R[2]=memory[num][2];
                R[3]=memory[num][3];
            }
            else if(IR[0]=='S' && IR[1]=='R')
            {
                String line = new String(IR);
                int num=Integer.parseInt(line.substring(2));
                memory[num][0]=R[0];
                memory[num][1]=R[1];
                memory[num][2]=R[2];
                memory[num][3]=R[3];
            }
            else if(IR[0]=='C' && IR[1]=='R')
            {
                String line = new String(IR);
                int num=Integer.parseInt(line.substring(2));
                if(memory[num][0]==R[0]&& memory[num][1]==R[1]&& memory[num][2]==R[2]&& memory[num][3]==R[3])
                {
                    T=1;
                }
                else
                {
                    T=0;

                }
            }
            else if(IR[0]=='B' && IR[1]=='T')
            {
                if(T==1)
                {
                    String line = new String(IR);
                    int num=Integer.parseInt(line.substring(2));
                    IC=num;
                    T=0;
                }
            }
            else if(IR[0]=='G' && IR[1]=='D')
            {
                SI=1;
                masterMode();
            }
            else if(IR[0]=='P' && IR[1]=='D')
            {
                SI=2;
                masterMode();
            }
            else if(IR[0]=='H')
            {
                SI=3;
                masterMode();
            }
        }
    }

    private void masterMode() {
        int i=this.SI;
        if(i==1)
        {
            Read();
        }
        else if(i==2)
        {
            Write();
        }
        else if(i==3)
        {

        }
        SI=0;
    }

    /**
     * write() : this method is used to write calculated output on file
     * */
    private void Write() {
//        IR[3]='0';
        String line = new String(IR);
        int num=Integer.parseInt(line.substring(2));
        String t,total="";
        for(int i=0;i<10;i++)
        {
            t=new String(memory[num+i]);
            total=total.concat(t);
        }
        System.out.println(total+"In write");
        try {
            output.write(total);
            output.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * READ ALGORITHM
     * ===============
     *
     * 1. IR[3] = '0'
     * 2. Fetch operand_address of instruction
     * 3. Read line from input file and store inside the buffer
     * 4. Iterate loop through buffer to move data at operand_adress inside the memory
     *
     * */
    private void Read() {
        System.out.println("Read Called! Memory Used: " + memory_used);
        IR[3]='0';
        String line = new String(IR);

        int num=Integer.parseInt(line.substring(2));


        try {
            line=fread.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        buffer=line.toCharArray();
        for (int i = 0; i < line.length();) {
            memory[num][(i%4)]=buffer[i];
            i++;
            if(i%4==0)
                num++;
        }
    }

    /**
     * INIT() : This method is used to reset all storage stuff like memory, IR, R, IC, T, SI, etc;
     * */
    public void init(){
        memory_used=0;
        memory=null;
        memory= new char[100][4];
        T=0;
        IC=0;
    }

    /**
     * printMemory() : This method is used to print memory array
     * */
    public void print_memory(){
        for(int i=0;i<100;i++) {
            System.out.println("memory["+i+"] "+new String(memory[i]));
        }
    }
}


























