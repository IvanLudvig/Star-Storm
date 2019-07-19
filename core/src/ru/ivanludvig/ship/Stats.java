package ru.ivanludvig.ship;

public class Stats {
	
	
	public static String[] names = 
			{"hi", "Oberon","Hydra","NightFall","Cygnus","WildCat","Proximo","Oblivion","Sirius","Pleadis","Blossom"};
	public static String[] 
	strength = {"||", "II", "I",   	"II", 		"III", 	 "III", 	"IV", 	 "VI", 		"V", 	 "VIII", 	"VII"};
	public static String[] 
	speed =    {"||", "III",  "V",  "III", 		"III",	 "IV", 	  	"III", 	 "II", 		"VII", 	 "II",		"III"};
	public static String[] 
	agility =  {"||", "IV", "V",   "I",  		"II", 	 "III", 	"III", 	 "IV",		"V", 	 "III", 	"V"};
	public static String[] 
	power =    {"||", "II", "I",    "II", 		"III", 	 "III", 	"III", 	 "IV", 		"V",	 "VIII", 	"VII"};
	public static String[] 
	weaponry = {"||", "I",  "I",    "II", 		"II", 	 "III", 	"IV", 	 "V", 		"V", 	"VIII", 	"VI"};
	
	public static float[] php =    {102, 87, 83, 98, 123, 110,107, 59, 78,  87, 220, 112,105, 52,  148, 125, 130, 76, 56, 93, 139};
	public static float[] pgr =    {5,   3,  2,  6,  4,   4,  3,   1,  2,   5,  8,   4,  4,   3,   3,   4,   5,   2,  3,  5,  4};	
	public static float[] pres =   {10,  7,  6,  8,  5,   4,  5,   9,  7,   10, 8,   6,  5,   10,  4,   6,   6,   7,  9,  8,  2};
	public static float[] prad =   {34,  37, 56, 61, 65,  71, 41,  38, 34,  44, 98,  73, 78,  23,  60,  45,  51,  28, 62, 84, 34};
	public static float[] pspeed = {7,   9,  11, 11, 5,   7,  9,   10, 7,   5,  2,   10, 6,   8,   3,   3,   11,  6,  2,  8,  3};
	public static float[] pdir =   {1,   1,  -1, -1, -1, -1,  1,   1,  1,   -1, -1,  -1, -1,  1,   1,   1,   1,   -1, -1, 1,  -1};
	public static float[] psp =    {1.3272998f, 0.80591553f, 1.6054225f, 1.5689403f, 0.070832476f, 1.8619994f, 1.3120154f, 0.07866035f, 
		0.95106834f, 1.83052f, 1.6837244f, 1.9856519f, 1.7059737f, 1.9200187f, 0.13097136f, 1.8249885f, 0.35958552f, 
		0.40076458f, 1.3473436f, 0.38856146f, 0.5694596f};
	public static int[] width =  {24, 24, 24, 24, 34, 34, 32, 34, 32, 30};
	public static int[] height = {14, 14, 20, 20, 22, 24, 26, 24, 34, 34};
	public static int[] range =  {24, 24, 25, 25, 36, 36, 34, 36, 36, 36};
	public static int[] price =  {100, 300, 625, 850, 1250, 1800, 2500, 3500, 3500, 3500};
	
	public static int hpFunc(int i){
		return (int) (Math.sqrt(getNum(i, 1))*80);
	}
	
	public static float speedFunc(int i){
		return (float)(getNum(i, 2)+3);
	}
	
	public static float minSpeedFunc(float speed, float agility){
		if(speed-((speed/agility)*2)>0){
			return speed-((speed/agility)*2);
		}else{
			return 1;
		}
	}
	
	public static float agilityFunc(int i){
		if(i!=3){
			return (float)(getNum(i, 3)+1);
		}else{
			System.out.println("Nightfall");
			return (float)(getNum(4, 3)+1)/1.24f;
		}
	}
	
	public static float powerFunc(int i){
		return (float)(getNum(i, 4)+5);
	}
	
	public static float weapFunc(int i, float power){
		return (float) ((float)0.06f*power*Math.sqrt((float)getNum(i, 5))+0.5f);
	}
	public static int getNum(int num, int type){
		switch(type){
		case 1:
			return con(strength[num]);
		case 2:
			return con(speed[num]);
		case 3:
			return con(agility[num]);
		case 4:
			return con(power[num]);
		case 5:
			return con(weaponry[num]);
		default:
			return -1;
		}
	}
	public static int con(String string){
		if(!string.contains("V")){
			return string.length();
		}else{
			if(string.equals("IV")){
				return 4;
			}else{
				return string.length()+4;
			}
			
		}
	}

}
