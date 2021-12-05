package org.hack.service;

import lombok.RequiredArgsConstructor;
import org.hack.domain.ModelLoader;
import org.hack.domain.ModelModifier;
import org.hack.domain.TownSearch;
import org.hack.domain.bean.Message;
import org.hack.domain.bean.ModelIn;
import org.hack.domain.bean.ModelOut;
import org.hack.domain.bean.Previsions;
import org.hack.infra.AleaLoader;
import org.hack.infra.CallAlan;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController("/")
@RequiredArgsConstructor
public class ModelService {
    private final ModelLoader loader;
    private final ModelModifier modifier;
    private final WebSocketListener listener;
    private final AleaLoader alea;

    @GetMapping(value = "/api/model", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ModelOut> call() {
        return loader.getData(new ModelIn());
    }

    @GetMapping(value = "/api/model/{firstDay}/{lastDay}/{intensity}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ModelOut> call(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date firstDay, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date lastDay, @PathVariable int intensity) {

        return modifier.alter(loader.getData(new ModelIn()), getDay(firstDay), getDay(lastDay), intensity);
    }

    @GetMapping(value = "/api/model/{firstDay}/{lastDay}/{intensity}/{firstDay2}/{lastDay2}/{intensity2}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ModelOut> call(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date firstDay, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date lastDay, @PathVariable int intensity,
                               @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date firstDay2, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date lastDay2, @PathVariable int intensity2) {

        return modifier.alter(modifier.alter(loader.getData(new ModelIn()), getDay(firstDay), getDay(lastDay), intensity), getDay(firstDay2), getDay(lastDay2), intensity2);
    }

    private int getDay(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return c.get(Calendar.DAY_OF_YEAR);
    }

    @GetMapping(value = "/api/find/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getTown(@PathVariable String code) {
        return TownSearch.getTown(code);
    }

    @GetMapping(value="/api/alea/{codePostal}/{annee}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Previsions> getPrevisions(@PathVariable String codePostal, @PathVariable String annee) {
        return alea.getPrevisions(codePostal, annee);
    }


}
