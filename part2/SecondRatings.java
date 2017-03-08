
/**
 * Write a description of SecondRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class SecondRatings {
    private ArrayList<Movie> myMovies;
    private ArrayList<Rater> myRaters;
    
    public SecondRatings() {
        // default constructor
        this("ratedmoviesfull.csv", "ratings.csv");
    }
    
    public SecondRatings(String moviefile, String ratingsfile) {
        FirstRatings fr = new FirstRatings();
        myMovies = fr.loadMovies(moviefile);
        myRaters = fr.loadRaters(ratingsfile);
    }
        
    public int getMovieSize() {
        return myMovies.size();
    }
    
    public int getRaterSize() {
        return myRaters.size();
    }
    
    private double getAverageByID(String id, int minimalRaters) {
        double totalRating = 0.0;
        int nRaters = 0;
        for (Rater r : myRaters) {
            double rating_by_rater = r.getRating(id);
            if (rating_by_rater >= 0) {
                totalRating += rating_by_rater;
                ++nRaters;
            }
        }
        
        if (nRaters < minimalRaters) {
            return 0.0;
        } else {
            return totalRating/nRaters;
        }        
    }
    
    public ArrayList<Rating> getAverageRatings(int minimalRaters) {
        ArrayList<Rating> ratedmovies = new ArrayList<Rating>();
        for (Movie m : myMovies) {
            double movie_rating = getAverageByID(m.getID(), minimalRaters);
            if (movie_rating > 0.0) {
                Rating r = new Rating(m.getID(), movie_rating);
                ratedmovies.add(r);
            }
        }
        return ratedmovies;
    }
    
    public String getTitle(String id) {
        for (Movie m : myMovies) {
            if (m.getID().equals(id)) {
                return m.getTitle();
            }
        }
        String notfound = "Movie ID# " + id + " is not found";
        return notfound;
    }
    
    public String getID(String title) {
        for (Movie m : myMovies) {
            if (m.getTitle().equals(title)) {
                return m.getID();
            }
        }
        String notfound = "NO SUCH TITLE";
        return notfound;        
    }
    
}
