/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jana.showcase.imdbclient;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author JANARTHANANS
 */
public class ImdbClient {
    
    String[] months = {
                    "January",
                    "February",
                    "March",
                    "April",
                    "May",
                    "June",
                    "July",
                    "August",
                    "September",
                    "October",
                    "November",
                    "december"
                };
    String[] imdbId = {"tt0111161","tt0068646","tt0071562","tt0468569","tt0050083","tt0108052","tt0110912","tt0060196","tt0167260","tt0137523","tt0120737","tt0080684","tt0109830","tt1375666","tt0073486","tt0167261","tt0133093","tt0099685","tt0076759","tt0047478","tt0317248","tt0114369","tt0102926","tt0114814","tt0038650","tt0118799","tt0110413","tt0064116","tt0816692","tt0245429","tt0120815","tt0120586","tt0034583","tt0021749","tt0054215","tt0082971","tt0047396","tt1675434","tt0027977","tt0120689","tt0103064","tt0253474","tt2582802","tt0407887","tt0088763","tt0209144","tt0172495","tt0078788","tt0057012","tt0043014","tt0482571","tt0078748","tt0110357","tt0032553","tt0405094","tt0095765","tt0081505","tt0050825","tt1853728","tt1345836","tt0910970","tt0169547","tt0095327","tt0090605","tt0033467","tt0053125","tt0364569","tt0119698","tt0052357","tt0082096","tt0086190","tt0087843","tt0022100","tt0211915","tt0051201","tt0105236","tt0112573","tt0435761","tt0066921","tt0180093","tt0075314","tt0036775","tt0056592","tt0056172","tt0338013","tt0093058","tt2096673","tt0086879","tt0070735","tt0045152","tt0040522","tt0062622","tt0208092","tt0071853","tt0012349","tt0114709","tt0119488","tt0059578","tt0042876","tt0361748","tt0053604","tt0042192","tt1832382","tt0097576","tt0040897","tt0017136","tt0055630","tt0086250","tt0372784","tt0053291","tt0041959","tt0105695","tt1187043","tt1049413","tt2106476","tt0081398","tt0363163","tt0119217","tt0057115","tt0095016","tt0071315","tt0018455","tt0113277","tt0047296","tt0457430","tt0031679","tt0050212","tt0096283","tt0015864","tt0089881","tt0044741","tt0050976","tt0083658","tt1305806","tt0050986","tt0120735","tt0017925","tt0080678","tt0112641","tt0347149","tt1291584","tt0993846","tt0434409","tt1205489","tt0055031","tt0118715","tt0268978","tt1392190","tt0032976","tt0077416","tt0892769","tt0061512","tt0031381","tt0116282","tt0117951","tt0046912","tt3659388","tt1255953","tt0167404","tt0758758","tt0025316","tt0266543","tt2267998","tt0978762","tt0084787","tt0477348","tt1979320","tt0033870","tt0266697","tt0395169","tt0046268","tt0079470","tt0091763","tt0064115","tt0469494","tt0074958","tt0053198","tt0060827","tt0092005","tt0052311","tt2024544","tt0093779","tt2278388","tt0107207","tt0245712","tt0405159","tt0075686","tt0032551","tt0052618","tt1130884","tt1028532","tt0087544","tt3011894","tt0046911","tt0079944","tt0083987","tt0036868","tt0107290","tt0032138","tt0401792","tt0440963","tt0246578","tt0056801","tt0044079","tt0112471","tt0114746","tt0088247","tt0338564","tt0107048","tt0120382","tt0198781","tt0058946","tt0353969","tt0848228","tt0073195","tt1201607","tt0075148","tt0072684","tt0072890","tt0083922","tt0050613","tt0113247","tt2015381","tt1220719","tt1504320","tt2084970","tt0044706","tt0058461","tt0325980","tt1392214","tt0092067","tt1454029","tt0038787","tt0046250","tt0061184","tt0374546","tt0070511","tt0101414","tt0048424","tt0052561","tt0381681","tt0047528","tt0111495","tt0118694","tt0053779","tt0061722","tt0044081","tt0069293","tt0056687","tt0049406"};
    String bareQuery = "INSERT INTO MOVIE (ID, TITLE, DESCRIPTION, STARS, DIRECTOR, CERTIFICATION, RELEASE_DATE," +
                   " RUNNING_TIME, VOTES, RATING, STORYLINE, GALLERY, GENRE, METASCORE, POSTER, RELEASE_YEAR)" +
                   " VALUES({0},{1},{2},{3},{4},{5},{6},{7},{8,number,#},{9},{10},{11},{12},{13},{14},{15});";
    
    /**
     * 
     * @param args 
     */
    public static void main(String[] args) {
        System.out.println("Imdb Client initiated");
        ImdbClient client =new ImdbClient();
        System.out.println("Movies:"+client.getMoviesfromFile().toString());
    }
    
    public List<Movie> getMovies() {
        Client client = ClientBuilder.newClient();
        URI imdbScrapeUrl = URI.create("http://getimdb.herokuapp.com/get/");
        WebTarget webTarget = client.target(imdbScrapeUrl);
        String[] temp = {"tt0012349","tt0040897","tt0015864","tt0044741","tt0050986","tt0017925","tt0046268","tt0046911","tt0058946","tt0353969","tt0050613","tt0374546","tt0047528"};
        return Arrays.asList(temp).stream().map(e-> {
            Movie movie = new Gson().fromJson(
                    webTarget.queryParam("id", e)
                            .request(MediaType.APPLICATION_JSON)
                            .get(String.class), Movie.class);
            movie.setId(e);
//            try {
//                movie.setVotes(String.valueOf(
//                        (movie.getVotes()!=null && movie.getVotes().length()>0)
//                                ?NumberFormat.getNumberInstance(java.util.Locale.US).parse(movie.getVotes())
//                                :""));
//            } catch (ParseException ex) {
//                Logger.getLogger(ImdbClient.class.getName()).log(Level.SEVERE, null, ex);
//            }
            return movie;
            }
        ).collect(Collectors.toList());
    }
    
    public List<Movie> getMoviesfromFile() {
        List<Movie> movies = null;
        try {
            movies = new ObjectMapper().readValue(
                    new File("C:\\Users\\janarthanans\\Work\\Practice\\movies-resource.txt"), 
                    new TypeReference<List<Movie>>(){});
        } catch (IOException ex) {
            Logger.getLogger(ImdbClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        int i = 0;
        for(Movie movie: movies) {
            movie.setId(imdbId[i]);
            i++;
            generateInsertQuery(movie);
        }
        return movies;
    }

    private String generateInsertQuery(Movie movie) {
        Number vote= null;
        LocalDate date=null;
        try {
            vote = NumberFormat.
                    getNumberInstance(java.util.Locale.US).parse(movie.getVotes());
        } catch (ParseException ex) {
//            Logger.getLogger(ImdbClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (movie.getReleaseDate()!=null && movie.getReleaseDate().length()>0) {
            String[] temporal = movie.getReleaseDate().split(" ");
            
            if(temporal.length==3) {
            int month = Arrays.asList(months).indexOf(temporal[1]);
            if(month>=0) {
                date = LocalDate.of(
                        Integer.valueOf(temporal[2]), 
                        Month.valueOf(String.valueOf(months[month].toUpperCase())), 
                        Integer.valueOf(temporal[0])
                );
            }
            }
        }
//        "ID, TITLE, DESCRIPTION, STARS, DIRECTOR, CERTIFICATION, RELEASE_DATE, "
//"RUNNING_TIME, VOTES, RATING, STORYLINE, GALLERY, GENRE, METASCORE, POSTER, RELEASE_YEAR"
        String populatedQuery = MessageFormat.format(
                bareQuery,
                "\'"+movie.getId().replace("\'", "''")+"\'",
                "\'"+movie.getTitle().replace("\'", "''")+"\'",
                "\'"+movie.getDescription().replace("\'", "''")+"\'",
                "\'"+movie.getStars().replace("\'", "''")+"\'",
                "\'"+movie.getDirector().replace("\'", "''")+"\'",
                "\'"+movie.getCertification().replace("\'", "''")+"\'",
//                "\'"+movie.getReleaseDate().replace("\'", "''")+"\'",
                (date!=null)?"\'"+date+"\'":null,
                "\'"+movie.getRunningTime().replace("\'", "''")+"\'",
                vote,
                movie.getRating(),
                "\'"+movie.getStoryline().replace("\'", "''")+"\'",
                "\'"+movie.getGallery()+"\'",
                "\'"+movie.getGenre().replace("\'", "''")+"\'",
                movie.getMetascore(),
                "\'"+movie.getPoster()+"\'",
                movie.getYear()
            );
//        System.out.println("Votes: "+movie.getVotes());
//        Number vote = (movie.getVotes()!=null&&movie.getVotes().length()>0)?Integer.valueOf(movie.getVotes().replace(",", "")):null;
//        System.out.println("Parsed votes: "+vote);
        System.out.println(populatedQuery);
        return populatedQuery;
    }
}
