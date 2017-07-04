import java.util.Random;
import java.util.Scanner;

/*Tugas Sistem Operasi
 * TI Reguler B
 * Semester III
 * 2015
 */

public class MainRR {
	static int jum_jobs, jum_ser;
	static String[] jobsname;
	static String[] sername;
	static int[] ser_jobquan;
	static Random random;
	static Scanner scn = new Scanner(System.in);
	static byte pl, lastMenu;
	static String temp;
	static int a, b, c, jumquan;
	static char[] statjobsname = new char[] { 'A', 'B', 'C', 'D', 'E', 'F' };

	public static void main(String[] arga) {
		System.out.println("\t====[Round Robbin Server]====\n");
		System.out.print(
				"\t\t[Main Menu]\n\t1.Static Mode\n\t2.Random Mode\n\t3.Manual Mode\n\t4.About\n\t5.Exit\n\tYour Decision : ");
		// input menu
		pl = scn.nextByte();
		switch (pl) {
		case 1: {
			RRStatic();
			break;
		}
		case 2: {
			RRRandom();
			break;
		}
		case 3: {
			RRManual();
			break;
		}
		case 4: {
			System.out.print("\n\t=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-" + "\n\tAbout Creator\n\n\tProgram Writer & Design"
					+ "\n\tRiyan Saputra Irawan(141105151104)" + "\n\tZainal Mubarok(141105151167)" + "\n\n\tSupport"
					+ "\n\tM.Naufal Fahmi(141105150720)" + "\n\tGhamal Nasser(141105150809)"
					+ "\n\tAbdul EriSusanto (141205151454)\n" + "\n\tversion 1.2" + "\n\tAll Rights Reserved"
					+ "\n\tUIKA Bogor 2015" + "\n\t=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-\n\n");
			main(null);
			break;
		}
		case 5: {
			System.exit(0);
			break;
		}
		default: {
			System.out.println("\n\tInput Didn't Valid...try again !");
			main(null);
			break;
		}
		}
	}

	static public void RRStatic() {
		System.out.print("\n\t[Static Mode]\n\tNote : Static condition to 6 jobs 3 servers\n\tJobs Queque");
		// serperti contoh di papan tulis.
		jum_jobs = 6;
		jobsname = new String[jum_jobs];
		for (a = 0; a < jobsname.length; a++) {
			jobsname[a] = "Jobs " + statjobsname[a];
			System.out.print("\n\t" + (a + 1) + ".Jobs Name\t:" + jobsname[a]);
		}

		System.out.println("\n\n\tServers");
		jum_ser = 3;
		sername = new String[jum_ser];
		ser_jobquan = new int[] { 3, 1, 2 };
		for (a = 0; a < jum_ser; a++) {
			sername[a] = "Server " + (a + 1);
			System.out.println("\t[" + sername[a] + "]\t> Set Job Quantum : " + ser_jobquan[a]);
		}
		RoundRobin();
		lastMenu = 1;
		SubMenu1();

	}

	static public void RRRandom() {
		System.out.print(
				"\n\t[Auto Dynamic Jobs & Servers]\n\tNote : Max Jobs 25,Max Servers 10,will generated automatically\n\n\tJobs Queque");
		random = new Random();
		// agar ter reset setiap repeat
		jum_jobs = 0;
		jum_ser = 0;
		while (jum_jobs <= 0 || jum_jobs > 25) {
			jum_jobs = random.nextInt(25);
		}
		jobsname = new String[jum_jobs];
		for (a = 0; a < jobsname.length; a++) {
			jobsname[a] = "Jobs-" + (a + 1);
			System.out.print("\n\t" + (a + 1) + ".Jobs Name\t:" + jobsname[a]);
		}

		System.out.println("\n\n\tServers :");
		while (jum_ser <= 0 || jum_ser > 10) {
			jum_ser = random.nextInt(10);
		}
		sername = new String[jum_ser];
		ser_jobquan = new int[jum_ser];

		for (a = 0; a < jum_ser; a++) {

			while (ser_jobquan[a] <= 0) {
				ser_jobquan[a] = random.nextInt(10);
			}
		}

		for (a = 0; a < jum_ser; a++) {
			sername[a] = "Server " + (a + 1);
			System.out.println("\t[" + sername[a] + "]\t> Set Job Quantum : " + ser_jobquan[a]);
		}
		RoundRobin();
		lastMenu = 2;
		SubMenu1();
	}

	static public void RRManual() {
		System.out.print("\n\t[Manual Mode]\n\tJobs Queue");
		// reset jumlah
		jum_jobs = 0;
		jum_ser	 = 0;
		while (jum_jobs < 1) {
			System.out.print("\n\tHow Many Jobs do you want ?\t\t: ");
			jum_jobs = scn.nextInt();
			if (jum_jobs < 1) {
				System.out.print("\tplease, fill at least 1!");
			}
		}
		jobsname = new String[jum_jobs];
		for (a = 0; a < jobsname.length; a++) {
			System.out.print("\tSet Name For Jobs " + (a + 1) + "\t\t\t: ");
			jobsname[a] = scn.next();
		}

		System.out.println("\n\tServers");
		while (jum_ser < 1) {
			System.out.print("\n\tHow Servers do you want ?\t\t: ");
			jum_ser = scn.nextInt();
			if (jum_ser < 1) {
				System.out.print("\tplease, fill at least 1!");
			}
		}
		sername = new String[jum_ser];
		ser_jobquan = new int[jum_ser];
		for (a = 0; a < jum_ser; a++) {
			System.out.print("\tSet Name For Servers " + (a + 1) + "\t\t\t: ");
			sername[a] = scn.next();
			sername[a] = "Server " + sername[a];
			while (ser_jobquan[a] < 1) {
				System.out.print("\tSet Job Quantum For " + sername[a] + "\t\t: ");
				ser_jobquan[a] = scn.nextInt();
			}
		}
		RoundRobin();
		lastMenu = 3;
		SubMenu1();

	}

	static public void RoundRobin() {
		b = 0;

		for (a = 0; a < jum_jobs; a++) {
			ser_jobquan[b] -= 1;

			if (ser_jobquan[b] >= 0) {
				System.out.print("\n\t> " + jobsname[a] + " to " + sername[b] + "\tAccepted \tLeft Job Quantum\t: "
						+ ser_jobquan[b]);
				b += 1;
			} else {
				System.out.print("\n\t> " + jobsname[a] + " to " + sername[b] + "\tUnAccepted\tOut Of Job Quantum");
				ser_jobquan[b] = 0;
				b += 1;
				a -= 1;
			}

			if (b > (jum_ser - 1)) {
				b = 0;
			}

			jumquan = 0;
			for (c = 0; c < jum_ser; c++) {
				jumquan += ser_jobquan[c];
			}
			// bila quantum job sudah habis
			if (jumquan == 0) {
				System.out.print("\n\n\t[ All Server is Full ! ]\n");
				break;
			}
		}
		// penampilan data sisa quantum
		if (jumquan > 0) {
			System.out.println("\n\n\tQuantum Job Server remnant :");
			for (int a = 0; a < jum_ser; a++) {
				if (ser_jobquan[a] < 1) {
					System.out.println("\tserver " + (a + 1) + " none left");
				} else {
					System.out.println("\tserver " + (a + 1) + " remnant : " + ser_jobquan[a]);
				}
			}
		}
	}

	static void SubMenu1() {
		System.out.print("\n\t1.Repeat This\n\t2.Main Menu\n\t3.Exit\n\tYour Decision : ");
		pl = scn.nextByte();
		switch (pl) {
		case 1: {
			if (lastMenu == 1) {
				RRStatic();
			} else if (lastMenu == 2) {
				RRRandom();
			} else if (lastMenu == 3) {
				RRManual();
			}
			break;
		}
		case 2: {
			main(null);
			break;
		}
		case 3: {
			System.exit(0);
			break;
		}
		default: {
			SubMenu1();
			break;
		}

		}
	}
}
