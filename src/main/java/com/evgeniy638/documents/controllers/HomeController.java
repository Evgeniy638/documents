package com.evgeniy638.documents.controllers;

import com.evgeniy638.documents.dto.FileInfoDTO;
import com.evgeniy638.documents.modules.User;
import com.evgeniy638.documents.services.FileService;
import com.evgeniy638.documents.services.GroupService;
import com.evgeniy638.documents.services.InstituteService;
import com.evgeniy638.documents.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final InstituteService instituteService;
    private final GroupService groupService;
    private final UserService userService;
    private final FileService fileService;

    @GetMapping(value = "/export/logs")
    public void export (HttpServletResponse httpServletResponse) throws IOException {
        String redirectUrl = "http://localhost:9000/api/search/universal/relative/export?query=*&range=1000&fields=message";

        httpServletResponse.setHeader("Location", redirectUrl);
        httpServletResponse.setStatus(302);
    }


    @GetMapping("/home")
    public String index(Model model, HttpServletRequest request) {
        String username = request.getUserPrincipal().getName();
        User user = userService.getUser(username);

        if (user == null) {
            return "logout";
        }

        Set<FileInfoDTO> userFiles = fileService.convertFileModToFileInfo(user.getFile());
        Set<FileInfoDTO> groupFiles = fileService.convertFileModToFileInfo(user.getGroup().getFile());
        Set<FileInfoDTO> institutionFiles = fileService
                .convertFileModToFileInfo(user.getGroup().getInstitute().getFile());

        model.addAttribute("username", username);
        model.addAttribute("institutes", instituteService.getInstitutes());
        model.addAttribute("groups", groupService.getGroups());
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("user", user);

        model.addAttribute("userFiles", userFiles);
        model.addAttribute("groupFiles", groupFiles);
        model.addAttribute("institutionFiles", institutionFiles);

        return "home";
    }
}
