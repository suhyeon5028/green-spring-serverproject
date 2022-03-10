package site.metacoding.serverproject.web;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import site.metacoding.serverproject.domain.Download;
import site.metacoding.serverproject.domain.DownloadRepository;

@RequiredArgsConstructor
@Controller
public class DownloadController {

    private final DownloadRepository downloadRepository;

    @GetMapping("/")
    public String main() {

        return "download";
    }

    @GetMapping("/download")
    public String download(Model model) {

        // 1. URL로 다운로드
        RestTemplate rt = new RestTemplate();
        Download[] response = rt.getForObject(
                "http://3.38.254.72:5000/api/hospital?sidoCdNm=부산&sgguCdNm=부산사하구",
                Download[].class);

        List<Download> downloadList = Arrays.asList(response);
        // System.out.println(downloadList.get(0));

        // 2. DB에 saveAll() + model에 담기
        downloadRepository.saveAll(downloadList);

        model.addAttribute("hospitals", downloadList);
        // 3. 리턴
        return "list";
    }
}
