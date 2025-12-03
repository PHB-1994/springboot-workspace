package edu.thejoeun.common.util;

// 파일 이미지를 업로드 할 때, 변수이름을 상세히 작성하는 것이 좋다.
// 프로필 이미지, 게시물 이미지, 상품 이미지

// lombok 은 대부분 DB 쪽
// import lombok.Value; // DB 관련 Value DB 컬럼값
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value; // 스프링부트 properties 에서 사용한 데이터
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

/**
 폴더 구조화 방식
 /product_images/
     /1001/               #상품 ID 로 폴더 생성
          main.jpg        # 유저가 선택한 명칭 그대로 메인이미지
          detail_1.jpg    # 상세 이미지 1
          detail_2.jpg    # 상세 이미지 2
     /1002/               #상품 ID 로 폴더 생성
          main.jpg        # 유저가 선택한 명칭 그대로 메인이미지
          detail_1.jpg    # 상세 이미지 1
          detail_2.jpg    # 상세 이미지 2

 파일명 규칙 방식
 /product_images/
     P1001_main.jpg       # 유저가 선택한 명칭 그대로 메인이미지
     P1001_detail_1.jpg   # 상세 이미지 1
     P1001_detail_2.jpg   # 상세 이미지 2

 UUID 사용 여부
    중소기업이나 내부 관리 시스템에서는 UUID 를 안쓰는 경우가 많다.
        상품ID + 순번
        상품코드 + 타입
        업로드타임스탬프
    대규모 서비스(쿠팡, 11번가 등)
    보안 상 상품 정보 노출 방지 등의 경우 활용
*/
@Service
@Slf4j
public class FileUploadService {

    // import org.springframework.beans.factory.annotation.Value;
    @Value("${file.upload.path}")
    private String uploadPath;

    @Value("${file.product.upload.path}")
    private String productUploadPath;

    /**
     * 프로필 이미지 업로드
     * @param file  업로드할 이미지 파일
     * @return  저장된 파일의 경로 (DB에 저장할 상대 경로)
     * @throws IOException  파일 처리 중 오류 발생 시 예외 처리
     */
    public String uploadProfileImage(MultipartFile file) throws IOException {
        // 파일이 비어있는지 확인
        if(file.isEmpty()) {
            throw new IOException("업로드할 파일이 없습니다.");
        }

        // 업로드 디렉토리 생성(폴더가 존재하지 않는 경우 디렉토리 = 폴더 컴퓨터 만드는 회사에서 지칭하는 명칭이 다름)
        File uploadDir = new File(uploadPath);
        if(!uploadDir.exists()){
            boolean created = uploadDir.mkdirs();
            if(!created){
                throw new IOException("업로드 디렉토리 생성에 실패했습니다." + uploadPath);
            }
            log.info("업로드 디렉토리 생성 : {}", uploadPath);
        }

        // 원본 파일명과 확장자 추출 originFileName
        String 클라이언트가업로드한파일이름 = file.getOriginalFilename();
        if(클라이언트가업로드한파일이름 == null || 클라이언트가업로드한파일이름.isEmpty()){
            throw new IOException("파일 이름이 유효하지 않습니다.");
        }
        // extension = 확장자 lastDotIndex = 마지막마침표의위치
        String 확장자 = "";
        int 마지막마침표의위치 = 클라이언트가업로드한파일이름.lastIndexOf('.'); // p.i.g.png 가능성이 있기 때문에 마지막의 . 만
        if(마지막마침표의위치 > 0) {
            확장자 = 클라이언트가업로드한파일이름.substring(마지막마침표의위치); // 마지막 마침표부터 끝까지 확장자라는 문자열에 저장 png jpg ...
        }

        // 고유한 파일명 생성 (UUID 사용) uniqueFileName = 하나_밖에_없는_파일이름
        String 하나_밖에_없는_파일이름 = UUID.randomUUID().toString() + 확장자;

        // filePath = 파일저장될경로  프로필사진이 위치할 폴더   +   폴더 내에서 겹칠 일이 없는 파일명칭
        Path 파일저장될경로 = Paths.get(   uploadPath,                   하나_밖에_없는_파일이름);

        // 파일 저장
        try {
            Files.copy(file.getInputStream(), 파일저장될경로, StandardCopyOption.REPLACE_EXISTING);
            log.info("프로필 이미지 업로드 성공 : {} -> {}", 클라이언트가업로드한파일이름, 하나_밖에_없는_파일이름);

        } catch(IOException e){
            log.error("파일 저장 중 오류 발생 : {}", e.getMessage());
            throw new IOException("파일 저장에 실패했습니다. : " + e.getMessage());
        }

        // DB 에서 저장할 상대 경로 반환 (웹에서 접근 가능한 경로) -- / 붙여야 하지 않나..? => 붙여야함 그리고 파일이 아닌 폴더를 반환하는게..?  => 맨 마지막 폴더에 파일을 추가한다고 생각하면 됨
        return "/profile_images/" + 하나_밖에_없는_파일이름;
    }


    /**
     * 상품 이미지 메인 이미지 업로드
     * @param file         업로드 할 상품 이미지 파일
     * @param productId    제품 업로드 시 상품 id
     * @param imageType    main, detail_1 detail_2 등
     * @return             저장된 파일의 경로 (DB 에 저장할 상대 경로)
     * @throws IOException 파일 처리 중 오류 발생 시 예외 처리
     *                     // 가져온 파일 임시저장 폴더 같은 곳에 파일 보관해두기
     */
    public String uploadProductImage(MultipartFile file, int productId, String imageType) throws IOException {

        // 파일이 비어있는지 확인
        if(file.isEmpty()) {
            throw new IOException("업로드할 파일이 없습니다");
        }

        // 상품 별 폴더를 생성하기 위한 폴더 변수명칭 설정
        //                  바탕화면/product_images  /  제품번호 로 폴더 생성
        String productFolder = productUploadPath + "/" + productId;

        // 업로드 디렉토리 생성
        File uploadDir = new File(productFolder);
        if(!uploadDir.exists()){
            boolean created = uploadDir.mkdirs();
            if(!created){
                throw new IOException("업로드 디렉토리 생성에 실패했습니다." + productFolder);
            }
            log.info("업로드 디렉토리 생성 : {}", productFolder);
        }

        // 과제 2 : 원본 파일명 그대로 사용하고 앞에 imageType 만 붙이기
        String 클라이언트가_업로드한_파일이름 = file.getOriginalFilename();
        if(클라이언트가_업로드한_파일이름 == null || 클라이언트가_업로드한_파일이름.isEmpty()){
            throw new IOException("파일 이름이 유효하지 않습니다.");
        }

        // 상품 가져오기
        // main - 클라이언트가 업로드한 파일 이름으로 저장하는 방법
        String fileName = imageType + "-" +클라이언트가_업로드한_파일이름; // 파일 확장자 기능은 따로 만들어서 사용
        // main.확장자명으로 저장되는 방법
        // String fileName = imageType + get확장자메서드(file); // 파일 확장자 기능은 따로 만들어서 사용

        // DB 에서 저장할 상대 경로 반환
        Path 저장될_파일_경로 = Paths.get(productFolder,fileName);

        // 파일 저장
        try {
            Files.copy(file.getInputStream(), 저장될_파일_경로, StandardCopyOption.REPLACE_EXISTING);
            log.info("상품 이미지 업로드 성공 : {} -> {}", file.getOriginalFilename(), fileName);

        } catch(Exception e) {
            log.error("상품 이미지 저장 중 오류 발생 : ", e.getMessage());
        }

        return "/product_images/" + productId + "/" + fileName;
    }


    private String get확장자메서드(MultipartFile f) {
        // 원본 파일명과 확장자 추출 originFileName
        String 클라이언트가업로드한파일이름 = f.getOriginalFilename();
        if(클라이언트가업로드한파일이름 == null || 클라이언트가업로드한파일이름.isEmpty()){
           return "";
        }
        // extension = 확장자 lastDotIndex = 마지막마침표의위치
        String 확장자 = "";
        int 마지막마침표의위치 = 클라이언트가업로드한파일이름.lastIndexOf('.'); // p.i.g.png 가능성이 있기 때문에 마지막의 . 만
        if(마지막마침표의위치 > 0) {
            return 클라이언트가업로드한파일이름.substring(마지막마침표의위치); // 마지막 마침표부터 끝까지 확장자라는 문자열에 저장 png jpg ...
        }

        return "";
    }

    // FileUploadService.java 에 deleteFile 이라는 메서드를 만들어 기존 이미지 파일 삭제
    //    private String deleteFile(int productId,MultipartFile file) throws IOException {
    //        if(file.isEmpty()) {
    //            throw new IOException("삭제할 파일이 없습니다");
    //        }
    //
    //    }
}
