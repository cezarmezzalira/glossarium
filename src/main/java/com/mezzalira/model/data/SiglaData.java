package com.mezzalira.model.data;

import com.mezzalira.model.entity.Sigla;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Cezar Mezzalira
 * Date: 14/12/13
 * Time: 17:12
 */
public interface SiglaData extends JpaRepository<Sigla, Integer>{
    List<Sigla> findBySigla(String sigla);

    @Query(value = "select * from siglas s where LOWER(s.sigla) like(:sigla)", nativeQuery = true)
    List<Sigla> findBySiglaLike(@Param("sigla") String sigla);

}
