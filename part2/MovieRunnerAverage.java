
/**
 * Write a description of class MovieRunnerAverage here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;
import edu.duke.*;

public class MovieRunnerAverage
{
    // instance variables - replace the example below with your own
    void printAverageRatings() {
        String moviefile = "./data/ratedmoviesfull.csv";
        String raterfile = "./data/ratings.csv";
        SecondRatings sr = new SecondRatings(moviefile, raterfile);
        
        int numMovies = sr.getMovieSize();
        int numRaters = sr.getRaterSize();
        
        //System.out.println("numMovies = " + numMovies);
        //System.out.println("numRaters = " + numRaters);
        
        int minimalRaters = 12;
        
        ArrayList<Rating> ratinglist = sr.getAverageRatings(minimalRaters);
        Collections.sort(ratinglist);
        
        int i = 0;
        
        for (Rating r : ratinglist) {
            if (i == 0) {
                System.out.println(r.getValue() + " " + sr.getTitle(r.getItem()));
                break;
            }

        }
    }
    
    void getAverageRatingOneMovie() {
        String moviefile = "./data/ratedmoviesfull.csv";
        String raterfile = "./data/ratings.csv";
        SecondRatings sr = new SecondRatings(moviefile, raterfile);
        
        String movietitle = "Vacation";
        String movieid = sr.getID(movietitle);
        int minimalRaters = 1;
        ArrayList<Rating> ratinglist = sr.getAverageRatings(minimalRaters);        
        
        for (Rating r: ratinglist) {
            if (movieid.equals(r.getItem())) {
                System.out.println("Average rating for the movie " + movietitle + " is " + r.getValue());
                break;
            }
        }
    }
}
