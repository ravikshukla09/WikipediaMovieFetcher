package com.thinkanalytics.rks.wiki.repository;

import com.thinkanalytics.rks.wiki.dto.MatchingMovies;
import com.thinkanalytics.rks.wiki.dto.MoviesList;
import com.thinkanalytics.rks.wiki.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    Optional<Movie> findByTitleIgnoreCase(String title);

    @Query(value = "SELECT a.movie_id, (a.count1 + b.count2) matchCount FROM " +
            "(SELECT movie_id, count(id) count1 FROM person where type = 'ACTOR' and name IN (:actors) group by movie_id) a," +
            "(SELECT movie_id, count(category_id) count2 FROM movie_categories where category_id IN (:categoryIds) GROUP BY movie_id) b " +
            "WHERE a.movie_id = b.movie_id " +
            "ORDER BY matchCount DESC LIMIT :limit", nativeQuery = true)
    List<Object[]> findMatchingMovies(@NotEmpty List<String> actors, @NotEmpty List<Integer> categoryIds, int limit);
}
