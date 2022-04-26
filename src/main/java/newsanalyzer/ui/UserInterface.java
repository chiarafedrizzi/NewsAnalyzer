package newsanalyzer.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

import newsanalyzer.ctrl.*;
import newsapi.*;
import newsapi.enums.*;

public class UserInterface
{

	private final Controller ctrl = new Controller();

	public void getDataFromCtrl1(){
		System.out.println("Aktuelles zu Corona");

		NewsApi newsApi = new NewsApiBuilder()
				.setApiKey(Controller.APIKEY)
				.setQ("corona")
				.setEndPoint(Endpoint.TOP_HEADLINES)
				.setSourceCountry(Country.at)
				.setSourceCategory(Category.health)
				.createNewsApi();

		try{
			ctrl.process(newsApi);
		}
		catch(ExceptionsController e) {
			System.out.println(e.getMessage());
		}
		catch( MalformedURLException | UrlExceptionController e){
			System.out.println("URL falsch");
		}
		catch(IOException e){
			System.out.println("We are sorry this happened");
		}
	}


	public void getDataFromCtrl2(){
		System.out.println("Alle aktuellen News aus Österreich");

		NewsApi newsApi = new NewsApiBuilder()
				.setApiKey(Controller.APIKEY)
				.setEndPoint(Endpoint.EVERYTHING)
				.setSourceCountry(Country.at)
				.createNewsApi();

		try{
			ctrl.process(newsApi);
		}
		catch(ExceptionsController e) {
			System.out.println(e.getMessage());
		}
		catch( MalformedURLException | UrlExceptionController e){
			System.out.println("URL falsch");
		}
		catch(IOException e){
			System.out.println("We are sorry this happened");
		}
	}


	public void getDataFromCtrl3(){
		System.out.println ("Aktuelle Science News aus Österreich" );

		NewsApi newsApi = new NewsApiBuilder()
				.setApiKey(Controller.APIKEY)
				.setQ("NEWS")
				.setFrom("2022-04-24")
				.setEndPoint(Endpoint.TOP_HEADLINES)
				.setSourceCategory(Category.science)
				.setSourceCountry(Country.at)
				.createNewsApi();

		try{
			ctrl.process(newsApi);
		}
		catch(ExceptionsController e) {
			System.out.println(e.getMessage());
		}
		catch( MalformedURLException | UrlExceptionController e){
			System.out.println("URL falsch");
		}
		catch(IOException e){
			System.out.println("We are sorry this happened");
		}

	}

	public void getDataForCustomInput() {
		System.out.println("Wonach soll gesucht werden?");
	}



	public void start() {
		Menu<Runnable> menu = new Menu<>("User Interface");
		menu.setTitel("Wählen Sie aus:");
		menu.insert("a", "Corona News", this::getDataFromCtrl1);
		menu.insert("b", "Alle aktuellen News aus Österreich", this::getDataFromCtrl2);
		menu.insert("c", "Aktuelle Science News aus Österreich", this::getDataFromCtrl3);
		menu.insert("d", "Choice User Input:",this::getDataForCustomInput);
		menu.insert("q", "Quit", null);
		Runnable choice;
		while ((choice = menu.exec()) != null) {
			choice.run();
		}
		System.out.println("Program finished");
	}


	protected String readLine() {
		String value = "\0";
		BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
		try {
			value = inReader.readLine();
		} catch (IOException ignored) {
		}
		return value.trim();
	}

	protected Double readDouble(int lowerlimit, int upperlimit) 	{
		Double number = null;
		while (number == null) {
			String str = this.readLine();
			try {
				number = Double.parseDouble(str);
			} catch (NumberFormatException e) {
				number = null;
				System.out.println("Please enter a valid number:");
				continue;
			}
			if (number < lowerlimit) {
				System.out.println("Please enter a higher number:");
				number = null;
			} else if (number > upperlimit) {
				System.out.println("Please enter a lower number:");
				number = null;
			}
		}
		return number;
	}
}
