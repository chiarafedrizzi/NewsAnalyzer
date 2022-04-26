package newsanalyzer.ctrl;

import newsapi.NewsApi;
import newsapi.beans.Article;
import newsapi.beans.NewsReponse;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Controller {

	public static final String APIKEY = "940a2707fa864554b01cfbcb43beaddd";

	public void process( NewsApi newsApi )throws IOException, ExceptionsController, UrlExceptionController {

		System.out.println("Start process");

		NewsReponse newsResponse = newsApi.getNews();
		if(newsResponse != null){
			List<Article> articles = newsResponse.getArticles();
			articles.forEach( article -> System.out.println(article.toString()));

			System.out.println("\nStatistik:\n");

			System.out.println("\nArtikel gesamt:\n");
			System.out.print( getArticlesNumber (articles));

			System.out.println("\nMeiste Artikel von:\n");
			System.out.print( getAuthorMostArticles (articles));

			System.out.println("\nKürzester Autorname:\n");
			getShortestAuthorName(articles)
					.forEach(article ->
							System.out.print(article.getAuthor()));

			System.out.println("\nAlphabetisch geordnet:\n");
			sortArticlesAlphabetically (articles)
					.forEach( article ->
							System.out.print(article.getTitle()));
		}

		System.out.println("End process");
	}

	public long getArticlesNumber(List<Article> data){
		return data.size ( );
	}

	public Article getAuthorMostArticles( List<Article> data){
		return data
				.stream()
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
				.entrySet()
				.stream()
				.max( Map.Entry.comparingByValue ( ) )
				.orElseThrow(NoSuchElementException::new )
				.getKey();
	}

	public List<Article> getShortestAuthorName(List<Article> data){
		return data
				.stream()
				.filter(article -> Objects.nonNull(article.getAuthor()))
				.sorted(Comparator.comparing(Article::getAuthor))
				.collect(Collectors.toList());
	}

	public List<Article> sortArticlesAlphabetically( List<Article> data){
		return data
				.stream()
				.sorted(Comparator.comparingInt(Article -> Article.getTitle().length()))
				.collect(Collectors.toList());
	}

	public Object getData(NewsApi newsApi) throws ExceptionsController, MalformedURLException {

		return newsApi.getNews();
	}

}