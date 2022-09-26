package com.ssafy.indive.global.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.UUID;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileUtils {

    private static String fileDirectory;

    @Value("${file.directory}")
    private void setFileDirectory(String fileDirectory) {
        FileUtils.fileDirectory = fileDirectory;
    }

    public static String saveFile(MultipartFile file) {

        String serverFileName = getSavedFileName(file);

        try {
            // 파일 객체를 생성한다.
            File folder = new File(getFullPath(serverFileName));

            // 해당 디렉토리에 폴더가 없다면 폴더를 생성한다.
            if (!folder.exists()) folder.mkdirs();

            // 파일을 저장한다.
            file.transferTo(folder);

        } catch (IOException e) {
            return null;
        }

        return serverFileName;
    }

    public static boolean deleteFile(String fileName) {
        // 파일 객체를 생성한다.
        File file = new File(getFullPath(fileName));

        // 파일을 삭제한다.
        return file.delete();
    }

    public static UrlResource getUrlResource(String fileName) {
        try {
            return new UrlResource("file:" + getFullPath(fileName));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getFullPath(String fileName) {
        return fileDirectory + File.separator + fileName;
    }

    private static String getSavedFileName(MultipartFile file) {
        // 클라이언트가 업로드하고자 하는 파일의 실제 이름이다.
        String originFilename = file.getOriginalFilename();

        // 확장자가 무엇인지 확인하기 위해 위치를 확인한다.

        int originExtensionIndex = originFilename.lastIndexOf(".");
        System.out.println(originExtensionIndex);

        // 랜덤 UUID값을 서버에 저장한다.
        String uuid = String.valueOf(UUID.randomUUID());

        // 실제 확장자 부분을 붙여 저장한다.
        String extension = originFilename.substring(originExtensionIndex);

        return uuid + extension;
    }
}
