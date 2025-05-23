package com.example.removie.kobis;


import com.example.removie.movie.vo.BasicMovieVO;
import java.util.*;


/**
 * 비교 프로세스에서 효율적인 탐색을 위한 객체입니다.
 *
 * @author An_Jicheol
 * @version 2.0
 */
public class NewReleaseMap {
    private final Map<String, BasicMovieVO> movieMap;

    public NewReleaseMap(Map<String, BasicMovieVO> movieMap) {
        this.movieMap = new HashMap<>(movieMap);
    }

    public Set<String> getMovieCodesSet() {
        return movieMap.keySet();
    }
    public List<String> getMovieCodeList(){return new ArrayList<>(movieMap.keySet());}

    public List<BasicMovieVO> getBasicMovieVOList() {
        return new ArrayList<>(movieMap.values());
    }

    public boolean isEmpty(){
        return movieMap.isEmpty();
    }
    public BasicMovieVO getBasicMovieVO(String movieCode){
        return movieMap.getOrDefault(movieCode, null);
    }

}
