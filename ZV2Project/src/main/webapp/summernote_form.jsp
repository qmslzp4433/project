<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"> -->
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<link rel="stylesheet" href="css/summernote-lite.css">
<script src="js/summernote-lite.js"></script>
<script src="js/lang/summernote-ko-KR.js"></script>


</head>
<body>
 <jsp:include page="Main_header.jsp"></jsp:include>
 
<textarea id="summernote" name="editordata"></textarea>

<script type="text/javascript">

	//여기 아래 부분
	$('#summernote').summernote({
		tabsize : 2,
		height : 400,
		width: 650,
		placeholder:'글 작성시 글씨 크기를 키우셔야 화면에 잘 보입니다'
	});

</script>

</body>
</html>