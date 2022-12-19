package com.example.bigsevaup.Controller;

import com.example.bigsevaup.Model.KategoryProduct;
import com.example.bigsevaup.Model.Product;
import com.example.bigsevaup.Model.Statistic;
import com.example.bigsevaup.Repository.KategoryProductRepository;
import com.example.bigsevaup.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.ArrayList;

@Controller
public class StatisticController {
    @Autowired
    ProductRepository productRepository;
    @GetMapping("stat")
    public String Statistic(Model model)
    {
        return ("statistic");
    }

    @GetMapping("/api/user/")
    public ResponseEntity<ArrayList> userallapi() {

        ArrayList<Statistic> a = new ArrayList<>();
        Iterable<Product> Products = productRepository.findAll();
        for (Product br:Products)
        {
            Statistic stat = new Statistic();
            stat.setkategory_product(productRepository.countBykategoryProduct_Id(br.getKategoryProduct().getId()));
            stat.setName(br.getnazvprod());
            a.add(stat);
        }
        return ResponseEntity.ok().body(a);
    }

    @GetMapping ("/BackupExport")
    public String backup()
            throws IOException, InterruptedException {
        String command = String.format("mysqldump -u%s --password=%s --add-drop-table --databases %s -r %s",
                "root", "", "sevaBIG", "C:\\OSPanel\\bec.sql");
        Process process = Runtime.getRuntime().exec(command);
        int processComplete = process.waitFor();
        if(processComplete==0)
        {
            return("redirect:/");}
        else
        { return("statistic");
        }
    }

    @GetMapping ("/BackupImport")
    public String backupImport()
            throws IOException, InterruptedException {
        String[] command = new String[]{
                "mysql",
                "-u" + "root",
                "--password=" + "",
                "-e",
                " source " + "C:\\OSPanel\\bec.sql",
                "sevaBIG"
        };
        Process runtimeProcess = Runtime.getRuntime().exec(command);
        int processComplete = runtimeProcess.waitFor();
        if(processComplete==0)
        {
            return("redirect:/");}
        else
        {
            return("statistic");
        }
    }
}