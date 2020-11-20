<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SC</title>
<style type="text/css">
#head{
 text-align: center;
 font-size: 25px;
 margin-top:60px;
}
#container{
	position: relative;
	top: 25px;
	width : 1000px;
	margin: 20px auto;
	padding : 10px;
}
.usm-join-box {
	height: 300px;
	margin-top: 5px;
	margin-bottom: 60px;
	padding: 20px;
	border: 1px solid #E1E1E3;
	overflow-y: scroll;
	background-color: #fff;
	font-size: 14px;
}
.usm-agree input[type="checkbox"] {
	cursor: pointer;
}
.usm-agree label {
	padding: 0 0 0 7px;
	cursor: pointer;
	font-size: 16px;
}
.remember label{
	font-size: 17px;
}
#botton{
	text-align: center;
}
input[type=checkbox]{
	margin-top: -3px;
}
</style>
</head>
<!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css"> -->
<script type="text/javascript">
function check(){
	var i_agree1 = document.getElementById('i_agree1').checked;
	var i_agree2 = document.getElementById('i_agree2').checked;
	
	if(i_agree1 == false){
		swal("약관동의 확인","서비스 이용약관 동의(필수)에 동의하여 주세요.");
		return false;
	}else if(i_agree2 == false){
		swal("약관동의 확인","개인정보 수집 동의(필수)에 동의하여 주세요.");
		return false;
	}
}
</script>
<body>
<%@include file="/WEB-INF/views/topLogin.jsp" %>
	<div id="head">
		<h1>SC 홈페이지 약관동의</h1>
	</div>
<div id="container">
	<form action="./singUpgo.do" method="post" id="frm" name="frm" onsubmit="return check()" >
	<input type="hidden" id="user_type" name="user_type" value=${user_type}>
		<div id="serviceAgree">
		<div class="remember">
			<label for="i_agree1"><input type="checkbox" id="i_agree1" name="i_agree1" value="1" style="margin-top: -3px;"> 서비스 이용약관 동의 안내 <strong> (필수)</strong></label>
		</div>
		<div class="usm-join-box">
			<dl>
				<dt>
					<strong>제 1조 (목적)</strong>
				</dt>
				<dd>본 회원약관은 SC(이하 '갑'라 한다)가 운영하는 인터넷관련 서비스(이하 '서비스'라 한다)를 이용함에 있어 
				관리자와 이용자(이하 '회원'라 한다)의 권리, 의무 및 책임사항을 규정함을 목적으로 합니다.</dd>
				<dd><br></dd>
				<dt>
					<strong>제 2조 (약관의 효력)</strong>
				</dt>
				<dd>1.본 약관은 '갑'에 회원 가입 시 회원들에게 통지함으로써 효력을 발생합니다.</dd>
				<dd>2.'갑'은 이 약관의 내용을 변경할 수 있으며, 변경된 약관은 제1항과 같은 방법으로 공지 또는 통지함으로써 효력을 발생합니다.</dd>
				<dd>3.약관의 변경사항 및 내용은 '갑'의 홈페이지에 게시하는 방법으로 공시합니다.</dd>
				<dd><br></dd>
				<dt>
					<strong>제 3조 (약관 이외의 준칙)</strong>
				</dt>
				<dd>이 약관에 명시되지 않은 사항이 전기 통신 기본법, 전기통신 사업법, 기타 관련 법령에 규정되어 있는 경우 그 규정에 따릅니다.</dd>
				<dd><br></dd><dt>
					<strong>제 4조 (이용계약의 체결)</strong>
				</dt>
				<dd>회원 가입 시 회원 약관 위에 있는 동의버튼을 누르면 약관에 동의하여 이 계약이 체결된 것으로 간주합니다.</dd>
				<dd><br></dd>
				<dt>
					<strong>제 5조 (용어의 정의)</strong>
				</dt>
				<dd>이 약관에서 사용하는 용어의 정의는 다음과 같습니다.</dd>
				<dd>1.회원: '갑'과 서비스 이용에 관한 계약을 체결한 자</dd>
				<dd>2.아이디(ID): 회원 식별과 회원의 서비스 이용을 위하여 회원이 선정하고 '갑'이 승인하는 문자와 숫자의 조합</dd>
				<dd>3.비밀번호: 회원이 통신상의 자신의 비밀을 보호하기 위해 선정한 문자와 숫자, 특수문자의 조합</dd>
				<dd><br></dd>
				<dt>
					<strong>제 6조 (이용신청)</strong>
				</dt>
				<dd>1.회원 가입은 온라인으로 가입신청 양식에 기록하여 '갑'에 제출함으로써 이용신청을 할 수 있습니다.</dd>
				<dd>2.가입희망 회원은 반드시 자신의 본명 및 본인이 사용하는 이메일,휴대전화로 이용신청을 하여야 하며, 1개의 ID만 신청을 할 수 있습니다.</dd>
				<dd><br></dd>
				<dt>
					<strong>제 7조 (회원가입의 승낙)</strong>
				</dt>
				<dd>'갑'의 회원 가입 신청 양식에 가입 희망 회원이 인터넷으로 제6조와 같이 신청하면 '갑'은 바로 가입을 승인하여 서비스를 이용할 수 있습니다.</dd>
				<dd><br></dd>
				<dt>
					<strong>제 8조(강제 탈퇴)</strong>
				</dt>
				<dd>1.회원가입신청이 승인이 된 후에도 마지막 로그인 일자가 1년이 지난 이후에는 회원의 자격을 강제 탈퇴시킬 수 있습니다.</dd>
				<dd>2. 회원가입신청이 승인이 된 후에도 허위사실의 기재가 발각되거나 '갑'의 명예를 회손시키거나 음란물이나 불건전한 내용을 게재할 경우 회원의 자격을 강제 탈퇴시킬 수 있습니다.</dd>
				<dd><br></dd>
				<dt>
					<strong>제 9조 (서비스 제공의 중지)</strong>
				</dt>
				<dd>1.설비의 보수 등을 위하여 부득이한 경우</dd>
				<dd>2.전기통신사업법에 규정된 기간통신사업자가 전기통신서비스를 중지하는 경우</dd>
				<dd>3.기타 '갑'이 서비스를 제공할 수 없는 사유가 발생한 경우</dd>
				<dd><br></dd>
				<dt>
					<strong>제 10조 ('갑'의 의무)</strong>
				</dt>
				<dd>1. '갑'은  계속적, 안정적으로 서비스를 제공할 수 있도록 최선의 노력을 다하여야 합니다.</dd>
				<dd>2.'갑'은 항상 회원의 신용정보를 포함한 개인신상정보의 보안에 대하여 관리에 만전을 기함으로서 회원의 정보보안에 최선을 다하여야 합니다</dd>
				<dd><br></dd>
				<dt>
					<strong>제 11조 (회원의 의무)</strong>
				</dt>
				<dd>1.회원은 관계법령, 이 약관의 규정, 이용안내 및 주의사항 등 '갑'이 통지하는 사항을 준수하여야 하며, 기타 '갑'의 업무에 방해되는 행위를 하여서는 안됩니다.</dd>
				<dd>2.회원은 '갑'의 사전 승낙 없이 서비스를 이용하여 어떠한 영리 행위도 할 수 없습니다.</dd>
				<dd>3.회원은 서비스를 이용하여 얻은 정보를 '갑'의 사전 승낙 없이 복사, 복제, 변경, 번역, 출판,방송 기타의 방법으로 사용하거나 이를 타인에게 제공할 수 없습니다.</dd>
				<dd>4.회원은 이용신청서의 기재내용 중 변경된 내용이 있는 경우 서비스를 통하여 그 내용을 '갑'에게 통지하여야 합니다.</dd>
				<dd>5.회원은 서비스 이용과 관련하여 다음 각 호의 행위를 하여서는 안됩니다.<br> 
  				 ①다른 회원의 아이디(ID)를 부정 사용하는 행위<br> 
  				 ②범죄행위를 목적으로 하거나 기타 범죄행위와 관련된 행위<br> 
  				 ③선량한 풍속, 기타 사회질서를 해하는 행위<br> 
  				 ④타인의 명예를 훼손하거나 모욕하는 행위<br>   
  				 ⑤타인의 지적재산권 등의 권리를 침해하는 행위<br>  
  				 ⑥해킹행위 또는 컴퓨터바이러스의 유포행위<br>   
  				 ⑦타인의 의사에 반하여 광고성 정보 등 일정한 내용을 지속적으로 전송 또는 타 사이트를 링크하는 행위<br>   
  				 ⑧서비스의 안전적인 운영에 지장을 주거나 줄 우려가 있는 일체의 행위<br> 
  				 ⑨기타 관계법령에 위배되는 행위<br>   
  				 ⑩게시판 등 커뮤니티를 통한 상업적 광고홍보 또는 상거래 행위</dd>
				<dd><br></dd>
				<dt>
					<strong>제 12조 (게시물 또는 내용물의 삭제) </strong>
				</dt>
				<dd>'갑'은 서비스의 게시물 또는 내용물이 제11조의 규정에 위반되거나 '갑' 소정의 게시기간을 초과하는 경우 사전 통지나 동의 없이 이를 삭제할 수 있습니다.</dd>
				<dd><br></dd>
				<dt>
					<strong>제 13조 (양도금지)</strong>
				</dt>
				<dd>회원이 서비스의 이용권한, 기타 이용계약상 지위를 타인에게 양도, 증여할 수 없으며, 이를 담보로 제공할 수 없습니다.</dd>
				<dd><br></dd>
				<dt>
					<strong>제 14조 (계약해지 및 이용제한) </strong>
				</dt>
				<dd>1.고용주 회원이 사업장 이용계약을 해지하고자 하는 때에는 이를 '갑'에게 신청하여야 합니다.</dd>
				<dd>2.'갑'은 해지 및 탈퇴를 한 회원이 다시 재가입을 희망 하는 경우 일정기간 제한할 수 있습니다.</dd>
				<dd><br></dd>
				<dt>
					<strong>제 15조 (면책·배상) </strong>
				</dt>
				<dd>'갑'은 서비스의 게시물 또는 내용물이 제11조의 규정에 위반되거나 '갑' 소정의 게시기간을 초과하는 경우 사전 통지나 동의 없이 이를 삭제할 수 있습니다.</dd>
				<dd>1.'갑'은 회원이 서비스에 게재한 정보, 자료, 사실의 정확성, 신뢰성 등 그 내용에 관하여는 어떠한 책임을 부담하지 아니하고,  회원은 자기의 책임아래 서비스를 이용하며, 서비스를 이용하여 게시 또는 전송한 자료 등에 관하여 손해가 발생하거나 자료의 취사 선택, 기타서비스 이용과 관련하여 어떠한 불이익이 발생 하더라도 이에 대한 모든 책임은 회원에게 있습니다.</dd>
				<dd>2.'갑'은 제11조의 규정에 위반하여 회원간 또는 회원과 제3자간에 서비스를 매개로 하는 거래 등과 관련하여 어떠한 책임도 부담하지 아니하고, 회원이 서비스의 이용과 관련하여 기대하는 이익에 관하여 책임을 부담하지 않습니다. </dd>
				<dd>3.회원 아이디(ID)와 비밀번호의 관리 및 이용상의 부주의로 인하여 발생 되는 손해 또는 제3자에 의한 부정사용 등에 대한 책임은 모두 회원에게 있습니다.</dd>
				<dd>4.회원이 제11조, 기타 이 약관의 규정을 위반함으로 인하여 '갑'이 회원 또는 제3자에 대하여 책임을 부담하게 되고, 이로써 '갑'에게 손해가 발생하게 되는 경우, 이 약관을 위반한 회원은 '갑'에게  발생하는 모든 손해를 배상하여야 하며, 동 손해로부터 '갑'을 면책시켜야 합니다.</dd>
				<dd><br></dd>
				<dt>
					<strong>제 16조 (분쟁의 해결) </strong>
				</dt>
				<dd>1.'갑'과 회원은 서비스와 관련하여 발생한 분쟁을 원만하게 해결하기 위하여 필요한 모든 노력을 하여야 합니다.</dd>
				<dd>2.1항의 규정에도 불구하고 분쟁으로 인하여 소송이 제기될 경우 소송은 '갑'의 소재지를 관할하는 법원의 관할로 합니다.</dd>
				<dd><br></dd>
			</dl>
		</div>
	</div>
	<div id="identiAgree">
		<div class="remember">
			<label for="i_agree2"><input type="checkbox" id="i_agree2" name="i_agree2" value="1" style="margin-top: -3px;"> 개인정보 수집 및 이용 안내 <strong> (필수)</strong></label>
		</div>
		<div class="usm-join-box">
			<dl>
				<dt>
					<strong>개인정보보호법 제15조(개인정보의 수집·이용)에 의거 홈페이지 회원가입을 위한
						개인정보를 다음과 같이 수집·이용하고자 합니다.</strong>
				</dt>
				<dd><br></dd>
				<dt>
					<strong>제 1조 (개인정보의 수집 및 이용 목적)</strong>
				</dt>
				<dd>SC 홈페이지는 다음과 같은 목적을 위하여 개인정보를 수집, 이용합니다.</dd>
				<dd> ①SC 홈페이지 통합 회원관리</dd>
				<dd> ②제한적 본인 확인제에 따른 본인확인, 비밀번호 분실 시 본인확인 등</dd>
				<dd> ③개인식별, 부정이용 방지, 비인가 사용방지, 가입의사 확인</dd>
				<dd> ④불만처리 등 민원처리, 공지사항 전달</dd>
                <dd><br></dd>    
				<dt>
					<strong>제 2조 (개인정보 수집에 관한 사항)</strong>
				</dt>
				<dd>① 필수항목 : 성명, 아이디, 비밀번호, 이메일, 휴대전화 번호, 주소, 생년월일</dd>
				<dd>② 선택항목 : 이메일 수신동의, SMS 수신동의</dd>
				<dd><br></dd>
				<dt>
					<strong>제 3조 (개인정보 보유 및 이용기간)</strong>
				</dt>
				<dd>귀하께서 입력하신 개인정보는 이용기간 동안 보유되며, 마지막 로그인 후 매
					1년이 되는 시점에 회원의 접속에 의해 그 기간은 연장되며, 회원 탈퇴시 개인정보는 6개월 이후 즉시 삭제됩니다.</dd>
				<dd>삭제 요청 및 홈페이지 탈퇴 시 까지의 개인정보는 회원으로 가입한 홈페이지의 개인정보처리방침에 의거 보유 및 이용됩니다.</dd>
				<dd>다만, 탈퇴자의 아이디는 동일한 아이디의 중복 가입을 방지하기
					위해 6개월간 보존하며, 기타 관계법령의 규정에 의하여 보존할 필요가 있는 경우 관계법령에 따릅니다.</dd>
				<dd><br></dd>
				<dt>
					<strong>제 4조 (개인정보 수집·이용에 대한 동의를 거부할 권리)</strong>
				</dt>
				<dd>귀하께서는 상기의 개인정보 수집 및 이용에 대하여 동의를 거부할 수 있습니다.</dd>
				<dd>필수항목의 경우 회원 가입 시 필요한 항목으로 동의 거부 시 회원가입이 제한되며, 선택 항목은
					동의하지 않아도 회원가입 및 서비스 거부를 하지 않습니다.(선택항목에 대하여 차후에 회원정보수정을 통하여 정보를
					입력할 수 있으며 입력된 항목은 동의를 한 것으로 간주 합니다.)</dd>
				<dd><br></dd>
				<dt>
					<strong>제 5조 (개인정보 수집·이용에 대한 보호)</strong>
				</dt>
				<dd>SC 홈페이지는 이용자의 정보수집시 서비스의 제공에 필요한 최소한의 정보를 수집합니다. </dd>
				<dd>제공된 개인정보는 당해 이용자의 동의없이 목적외의 이용이나  제3자에게 제공할 수 없으며, 이에 대한 모든 책임은 '갑'이 집니다. <br>
				다만, 다음의 경우에는 예외로 합니다.<br> 
 				 ①통계작성, 학술연구 또는 시장조사를 위하여 필요한 경우로서 특정 개인을 식별할 수 없는 형태로 제공하는 경우<br>  
  				 ②전기통신기본법 등 법률의 규정에 의해 국가기관의 요구가 있는 경우<br>
  				 ③범죄에 대한 수사상의 목적이 있거나 정보통신윤리 위원회의 요청이 있는 경우<br>
 				 ④기타 관계법령에서 정한 절차에 따른 요청이 있는 경우</dd>
  				<dd>회원은 언제든지 자신의 개인정보에 대해 열람 및 오류정정을 요구할 수 있으며 SC 홈페이지 측은 이에 대해 지체없이 처리합니다.</dd>
			</dl>
		</div>
		<div id="botton">
			<input type="submit" class="btn btn-primary" value="동의하고 회원가입">
			<input type="button" class="btn btn-basic" value="돌아가기"
				onclick="javascript:location.replace('./loginForm.do')">
		</div>
	</div>
	</form>
</div>
</body>
</html>