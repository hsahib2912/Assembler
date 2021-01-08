//HARKISHAN SINGH (2017233)			HIMANSHU NANADA(2017237)
import java.util.*;
import java.io.*;



class STable{
	String operand;
	int address,value,pass2help=1,tlc;
	STable(String s,int addressf,int valuef,int tlc){
		this.operand=s;
		this.address=addressf;
		this.value=valuef;
		this.tlc=tlc;
	}
}


class LTable{
	int literal,address,pass2help=1;;
	LTable(int literalf,int addressf){
		this.literal=literalf;
		this.address=addressf;
	}
}



class opTable{
	String opcode,operand,bincode;
	final int intstructionLength=12;
	int pass2help=1,tlc;
	opTable(String bincodef,String opcodef,String operandf,int tlc){
		this.bincode=bincodef;
		this.opcode=opcodef;
		this.operand=operandf;
		this.tlc=tlc;
	}
}



class code_2017233_2017237{
	static int lc=0;
	static LinkedList<STable> sym =new LinkedList<STable>();
	static LinkedList<LTable> lit=new LinkedList<LTable>();
	static LinkedList<opTable> op=new LinkedList<opTable>();
	public static void main(String[] args) throws Exception {
		pass1();
		pass2();
	}
	public void settables(){

	}



	public static void pass1() throws Exception{
		BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
		String s;
		System.out.println("\t\t PASS 1 : \n\n");
		while((s = input.readLine()) != null){
			StringTokenizer st = new StringTokenizer(s, " ");
			int tmp=st.countTokens();
			lc++;
			if(tmp==1){
				String opcode=st.nextToken();
				op.add(new opTable(getbincode(opcode),opcode,"",lc));

			}
			if(tmp==2){
				String opcode=st.nextToken();
				String operand=st.nextToken();
				op.add(new opTable(getbincode(opcode),opcode,operand,lc));
				sym.add(new STable(operand,-1,-1,lc));
			}
			if(tmp==3){
				String symbol=st.nextToken();
				String ds=st.nextToken();
				int value=Integer.parseInt(st.nextToken());
				for (int i=0;i<sym.size();++i){
					if(((sym.get(i)).operand).equals(symbol) && (sym.get(i)).address==-1){
						(sym.get(i)).address=lc;
						(sym.get(i)).value=value;
						(sym.get(i)).tlc=lc;
					}
				}
				lit.add(new LTable(value,lc));
			}
		}
		System.out.println("\t\tOPCODE TABLE FOLLOWS");
		System.out.println("\t\t--------------------\n\n");
		System.out.println("BINARY CODE"+"\t\t"+"OPCODE"+"\t\tOPERAND");
		for(int i=0;i<op.size();++i){
			System.out.println((op.get(i)).bincode+"\t\t\t"+(op.get(i)).opcode+"\t\t"+(op.get(i)).operand);
		}
		System.out.println("\n\t\tSYMBOL TABLE FOLLOWS");
		System.out.println("\t\t--------------------\n\n");
		System.out.println("SYMBOL"+"\t\t"+"ADDRESS");
		for(int i=0;i<sym.size();++i){
			if((sym.get(i)).address!=-1){
				System.out.println((sym.get(i)).operand+"\t\t"+Integer.toBinaryString((sym.get(i)).address));
			}
		}
		System.out.println("\n\t\tLITERAL TABLE FOLLOWS");
		System.out.println("\t\t--------------------\n\n");
		System.out.println("LITERAL\t\t"+"ADDRESS");
		for(int i=0;i<lit.size();++i){
			System.out.println((lit.get(i)).literal+"\t\t"+Integer.toBinaryString((lit.get(i)).address));
		}
	}




	public static void pass2() throws Exception{
		BufferedReader input=new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
		String s;
		System.out.println("\n\n\n\t\tPASS 2 : ");
		File file=new File("/Users/harkishansingh/Desktop/objectcode.txt");
		FileWriter fileWriter=new FileWriter("objectcode.txt");
		PrintWriter printWriter= new PrintWriter(fileWriter);
		int tlc=0;
		System.out.println("\n\n\n\t\tOBJECT CODE FOLLOWS");
		System.out.println("\t\t--------------------\n\n");
		while((s = input.readLine()) != null){
			StringTokenizer st=new StringTokenizer(s," ");
			int tmp=st.countTokens();
			String binc="";
			int address=0;
			if(tmp==1 ){

				binc=getbincode(st.nextToken());
				if(binc.equals("0000"))
				{
					tlc=0;
				}
			}
			if(tmp==2){
				binc=getbincode(st.nextToken());
				String symb=st.nextToken();
				for(int i=0;i<sym.size();++i){
					if(((sym.get(i)).operand).equals(symb) && (sym.get(i)).pass2help==1){
						address=(sym.get(i)).address;
						(sym.get(i)).pass2help=0;
						break;
					}
				}
				if(address==-1){address=0;}
			}
			if(tmp==3){
				String symb=st.nextToken();
				for(int i=0;i<sym.size();++i){
					if(((sym.get(i)).operand).equals(symb) && (sym.get(i)).pass2help==0){
						address=(sym.get(i)).address;
						(sym.get(i)).pass2help=-1;
						break;
					}
				}
			}
			if(binc.equals("") && address!=0){
				System.out.println("-\t"+Integer.toBinaryString(address)+"\t"+Integer.toBinaryString(tlc));
				printWriter.printf("-\t"+Integer.toBinaryString(address)+"\t"+Integer.toBinaryString(tlc));
			}
			else if(address==0 && binc.length()!=0){
				System.out.println(binc+"\t-\t"+Integer.toBinaryString(tlc));
				printWriter.printf(binc+"\t-\t"+Integer.toBinaryString(tlc));
			}
			else if(binc.equals("") && address==0){
				System.out.println("-\t-\t"+Integer.toBinaryString(tlc));
				printWriter.printf("-\t-\t"+Integer.toBinaryString(tlc));
			}
			else{
				System.out.println(binc+"\t"+Integer.toBinaryString(address)+"\t"+Integer.toBinaryString(tlc));
				printWriter.printf(binc+"\t"+Integer.toBinaryString(address)+"\t"+Integer.toBinaryString(tlc));
			}
			tlc++;
		}
		printWriter.close();
	}
	public static String getbincode(String opcode){
		String k="";
			if(opcode.equals("CLA")){
				lc=0;k="0000";
			}
			else if(opcode.equals("LAC")){k="0001";}
			else if(opcode.equals("SAC")){k="0010";}
			else if(opcode.equals("ADD")){k="0011";}
			else if(opcode.equals("SUB")){k="0100";}
			else if(opcode.equals("BRZ")){k="0101";}
			else if(opcode.equals("BRN")){k="0110";}
			else if(opcode.equals("BRP")){k="0111";}
			else if(opcode.equals("INP")){k="1000";}
			else if(opcode.equals("DSP")){k="1001";}
			else if(opcode.equals("MUL")){k="1011";}		
			else if(opcode.equals("DIV")){k="1011";}
			else if(opcode.equals("STP")){k="1100";}
		return k;
	}
}