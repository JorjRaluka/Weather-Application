package service;

import repository.Repository;
import repository.weatherInterval;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Service {
    private Repository repo;

    public Service(Repository repo) {
        this.repo = repo;
    }
    public List<weatherInterval>getAllWeatherIntervals(){
        return repo.getAllWeatherIntervals();
    }
    public List<weatherInterval>filterWeatherIntervals(int maxPrecip,int minTemp){
        return repo.getAllWeatherIntervals().stream()
                .filter(interval->interval.getPrecip()<maxPrecip && interval.getTemp()>minTemp)
                .sorted((a,b)->Integer.compare(a.getStartH(),b.getEndH()))
                .collect(Collectors.toList());
    }
    public double avgTemp(int startH, int endH) {
        return repo.getAllWeatherIntervals().stream()
                .filter(interval -> interval.getStartH() >= startH && interval.getEndH() <= endH)
                .mapToDouble(weatherInterval::getTemp)
                .average()
                .orElse(Double.NaN);
    }


    public void updateDescription(int startH,int endH,String newDescr){
        repo.updateDescription(startH,endH,newDescr);
    }
}
