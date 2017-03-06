import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;

public class FirstRatings
{
    ArrayList<Movie> loadMovies(String filename) {
        ArrayList<Movie> movies = new ArrayList<Movie>();
        
        FileResource fr = new FileResource(filename); //Open a dialogue box that lets you choose the file
        CSVParser parser = fr.getCSVParser();
        for (CSVRecord record : parser) {
            String id = record.get("id");
            String title = record.get("title");
            String year = record.get("year");
            String country = record.get("country");
            String genre = record.get("genre");
            String director = record.get("director");
            int minutes =  Integer.parseInt(record.get("minutes"));
            String posterURL = record.get("poster");
            
            Movie m = new Movie(id, title, year, genre, director, country, posterURL, minutes);
            movies.add(m);
            
        }
        
        return movies;
    }
    
    void testLoadMovies() {
        
        //ArrayList<Movie> movies = loadMovies("./data/ratedmovies_short.csv");
        ArrayList<Movie> movies = loadMovies("./data/ratedmoviesfull.csv");        
        System.out.println("Total number of movies = " + movies.size());
        
        
        HashMap<String, Integer> directormap = new HashMap<String, Integer>();
        
        int n_comedy = 0;
        int n_more_than_150_minutes = 0;
        
        for (Movie m : movies) {
            if (m.getGenres().toLowerCase().contains("comedy")) {
                ++n_comedy;
            }
            
            if (m.getMinutes() > 150) {
                ++n_more_than_150_minutes;
            }
            
            String[] directors = m.getDirector().split(",");
            for (int i = 0; i < directors.length; ++i) {
                String dir = directors[i].trim();
                if (directormap.keySet().contains(dir)) {
                    directormap.put(dir, directormap.get(dir) + 1);
                } else {
                    directormap.put(dir, 1);
                }
            }
            //System.out.println(m.toString());
        }
        
        System.out.println("number of comedy movies = " + n_comedy);
        System.out.println("number of movies longer than 150 minutes = " + n_more_than_150_minutes);
        
        String max_director = "";
        int max_directed_movies = 0;
        
        for (HashMap.Entry<String, Integer> entry : directormap.entrySet()) {
            //System.out.println("director = " + entry.getKey() + ", n_movie = " + entry.getValue());
            if (entry.getValue() > max_directed_movies) {
                max_director = entry.getKey();
                max_directed_movies = entry.getValue();
            }
        }
        
        System.out.println("max_director = " + max_director + ", total movies directed = " + max_directed_movies);
                
    }
    
    ArrayList<Rater> loadRaters(String filename) {
        ArrayList<Rater> raters = new ArrayList<Rater>();
        HashMap<String, Rater> rater_map = new HashMap<String, Rater>();
        FileResource fr = new FileResource(filename); 
        CSVParser parser = fr.getCSVParser();
        
        for (CSVRecord record : parser) {
            String rater_id = record.get("rater_id");
            String movie_id = record.get("movie_id");
            double rating_score = Double.parseDouble(record.get("rating"));
            double time = Double.parseDouble(record.get("time"));
 
            if (rater_map.keySet().contains(rater_id)) {
                rater_map.get(rater_id).addRating(movie_id, rating_score);
            } else {
                Rater new_rater = new Rater(rater_id);
                new_rater.addRating(movie_id, rating_score);                
                rater_map.put(rater_id, new_rater);
            }
        }
        
        for (String entry : rater_map.keySet()) {
            raters.add(rater_map.get(entry));
        }
        
        return raters;    
    }
    
    void testLoadRaters() {
        ArrayList<Rater> raters = loadRaters("./data/ratings.csv");        
        System.out.println("Total number of raters = " + raters.size());
        
        String rater_lookup = "193";
        
        //Rater found_rater = raters.get(rater_lookup - 1);
        //System.out.println("Number of movies rated by rater #" + rater_lookup + " is equal to " + found_rater.numRatings());
        
        for (Rater r: raters) {
            if (r.getID().equals(rater_lookup)) {
                System.out.println("Number of movies rated by rater #" + r.getID() + " is equal to " + r.numRatings());
            }
        }
        
        HashMap<Integer, ArrayList<String>> raters_productivity = new HashMap<Integer, ArrayList<String>>();
        int max_productivity = 0;
        
        for (Rater r : raters) {
            if (raters_productivity.keySet().contains(r.numRatings())) {
                raters_productivity.get(r.numRatings()).add(r.getID());
            } else {
                ArrayList<String> raters_list = new ArrayList<String>();
                raters_list.add(r.getID());
                raters_productivity.put(r.numRatings(), raters_list);
            }
            
            if (r.numRatings() > max_productivity) {
                max_productivity = r.numRatings();
            }
        }
        
        System.out.println("The maximum number of ratings = " + max_productivity + ", provided by ");
        for (String rater_id : raters_productivity.get(max_productivity) ) {
            System.out.println(" rater id = " + rater_id);
        }
        
        String movie_lookup = "1798709";
        int found_ratings = 0;
        
        for (Rater r: raters) {
            if (r.hasRating(movie_lookup)) {
                ++found_ratings;
            }
        }
        
        System.out.println("The movie with IMDB id of " + movie_lookup + " was rated by " + found_ratings + " raters");
        
        HashSet<String> movielist = new HashSet<String>();
        
        for (Rater r: raters) {
            for (String movie_id : r.getItemsRated()) {
                movielist.add(movie_id);
            }
        }
        
        System.out.println("Total movies rated by all these raters are " + movielist.size());
    }
        
}
