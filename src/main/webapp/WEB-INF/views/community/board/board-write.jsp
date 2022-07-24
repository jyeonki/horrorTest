<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">

<head>
<%@ include file="../include/static-head.jsp" %>

<style>
    .write-container {
        width: 50%;
        margin: 200px auto 150px;
        font-size: 1.2em;
    }
</style>
</head>

<body>
    <div class="wrap">
        <%@ include file="../include/header.jsp" %>

        <div class="write-container">

            <form id="write-form" action="/horror/community/write" method="post" autocomplete="off">

                <div class="mb-3">
                    <label for="writer-input" class="form-label">작성자</label>
                    <input type="text" class="form-control" id="writer-input" placeholder="이름"
                        name="writer" maxlength="20">
                </div>
                <div class="mb-3">
                    <label for="category-input" class="form-label">카테고리</label>
                    <c:forEach var="ctgr" items="${ctgrList}">
                        <label for="category_${ctgr.code}"><input type='radio' id="category_${ctgr.code}" class="form-check-input" name='category' value="${ctgr.code}">${ctgr.codeNm}</label>
                    </c:forEach>                
                </div>
                <div class="mb-3">
                    <label for="title-input" class="form-label">글제목</label>
                    <input type="text" class="form-control" id="title-input" placeholder="제목" name="title">
                </div>
                <div class="mb-3">
                    <label for="exampleFormControlTextarea1" class="form-label">내용</label>
                    <textarea name="content" class="form-control" id="exampleFormControlTextarea1" rows="10"></textarea>
                </div>

                <div class="d-grid gap-2">
                    <button id="reg-btn" class="btn btn-dark" type="button">글 작성하기</button>
                    <button id="to-list" class="btn btn-warning" type="button">목록으로</button>
                </div>
                
                <input type="hidden" class="form-control" id="id-input" name="id">
                <!--<input type="hidden" class="form-control" id="category-input" name="category">-->

            </form>

        </div>

        <%@ include file="../include/footer.jsp" %>

        
    </div>


    <script>


        // 게시물 등록 입력값 검증 함수
        function validateFormValue() {
            // 이름 입력태그, 제목 입력태그
            const $writerInput = document.getElementById('writer-input');
            const $titleInput = document.getElementById('title-input');

            let flag = false; // 입력 제대로 하면 true로 변경

            console.log('w: ', $writerInput.value);
            console.log('t: ', $titleInput.value);

            if ($writerInput.value.trim() === '') {
                alert('작성자는 필수 입력값입니다');
            } else if ($titleInput.value.trim() === '') {
                alert('제목은 필수 입력값입니다');
            } else {
                flag = true;
            }

            console.log('flag: ', flag);
            return flag;
        }

        // 게시물 입력값 검증
        const $regBtn = document.getElementById('reg-btn');

        $regBtn.onclick = e => {
            // 입력값을 제대로 채우지 않았는지 확인
            if (!validateFormValue()) { // 필수 입력값을 채우지 않았다면
                alert('입력값을 확인하세요!') // alert 말고 placeholder도 가능 (빨간색으로 input칸이 변경된다던가?)
                return;
            }

            // 카테고리 입력
            //const $category = document.getElementById('category-input');
            //$category.value = ;
            
            // ID에 기본값 입력
            const $userID = document.getElementById('userID');
            const $id = document.getElementById('id-input');
            $id.value = $userID.value;

            // 필수 입력값을 잘 채웠으면 form summit
            const $form = document.getElementById('write-form');
            $form.submit();
        };

        // 목록버튼 이벤트
        const $toList = document.getElementById('to-list');
        $toList.onclick = e => {
            location.href = '/horror/community/list';
        };
    </script>

</body>

</html>