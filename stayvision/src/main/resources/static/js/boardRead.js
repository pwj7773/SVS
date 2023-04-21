	$(document).ready(function(){
		loadReply();
		// 이벤트달기(다른 이벤트가 실행 될떄마다 on 매서드가 실행되는 것을 막기 위해서)
		// 위로 가져옴
		$("#btnUpdate").on("click",function(){
			updateReply();
		});
		
		$("#btnReply").on("click",function(){
			
			insertReply();
			
		});
		
		
		$("#btnUpdate").hide();
		
		// btnRec라는 id를 버튼에 이벤트를 추가
		$("#btnRec").on("click",function(){ 
			// 여기에서 ajax로 server와 통신할거야~
			
			// document.getElememtById("boardNum").value;
			let bNum = $("#boardNum").val();
			//alert(bNum);
			$.ajax({
				// 내가 필요한 설정들을 써서 서버에 보낼 수 있음~~!
				url: "/recommend", // 호출할 서버의 url
				method: "post", // get또는 psot
				data: {"boardNum" : bNum}, //url을 호출하면서 넘겨줄 data
				success: function(data) {
					if(isNaN(data)){
						alert(data);
					}else{
						$("#rec").text(data);
					}
				}
			});
		});
	});
	
	function insertReply(){
		// 댓글 정보 가져오기(댓글 내용)
		let replyText = $("#replyText").val();
		let boardNum = $("#boardNum").val();
		// ajax로 댓글 정보 보내주면 되겠다
		$.ajax({
			url : "/insertReply",
			method : "post",
			data : {"replyText" : replyText, "boardNum" : boardNum},
			success : function(){
				$("#replyText").val(""); //입력창 초기화
				loadReply();
			}
		});
	
	}// end of insertReply
	
	//댓글 목록 가져오는 ajax 호출하는 함수
	function loadReply(page){
		// 글 번호 가져오기
		let boardNum = $("#boardNum").val();	// hidden 태그에 달린 글 번호
		
		$.ajax({
			url : "/loadReply",
			method : "post",
			data : {"boardNum" : boardNum, "page" : page},
			success: function(data){
				const pagingDiv = document.getElementById('paging');
				const currentPage = data.navi.currentPage;
				const pagePerGroup = data.navi.pagePerGroup;
				const startPageGroup = data.navi.startPageGroup;
				const endPageGroup = data.navi.endPageGroup;

				pagingDiv.innerHTML = '';
				const container = document.createElement('ul');
				container.classList.add('pagination', 'justify-content-center');

				const liPrev = document.createElement('li');
				liPrev.classList.add('page-item');
				container.appendChild(liPrev);

				const aPrev = document.createElement('a');
				aPrev.classList.add('page-link');
				aPrev.setAttribute('aria-label', 'Previous');
				aPrev.href = `javascript:loadReply(${currentPage - pagePerGroup})`;
				liPrev.appendChild(aPrev);

				const spanPrev = document.createElement('span');
				spanPrev.setAttribute('aria-hidden', 'true');
				spanPrev.innerText = '«';
				aPrev.appendChild(spanPrev);

				const li = document.createElement('li');
				li.classList.add('page-item');
				container.appendChild(li);

				const a = document.createElement('a');
				a.classList.add('page-link');
				a.setAttribute('aria-label', 'Previous');
				a.href = `javascript:loadReply(${currentPage - 1})`;
				li.appendChild(a);

				const span = document.createElement('span');
				span.setAttribute('aria-hidden', 'true');
				span.innerText = '＜';
				a.appendChild(span);


				for (let counter = startPageGroup; counter <= endPageGroup; counter++) {
				  if (counter !== 0) {
				    const li = document.createElement('li');
				    li.classList.add('page-item');
				    const a = document.createElement('a');
				    a.classList.add('page-link');
				    a.href = `javascript:loadReply(${counter})`;
				    a.innerText = counter;
				    li.appendChild(a);
				    container.appendChild(li);
				  } 
				}
				
				const liNextPage = document.createElement('li');
				liNextPage.classList.add('page-item');
				container.appendChild(liNextPage);

				const aNextPage = document.createElement('a');
				aNextPage.classList.add('page-link');
				aNextPage.setAttribute('aria-label', 'Next');
				aNextPage.href = `javascript:loadReply(${currentPage + 1})`;
				liNextPage.appendChild(aNextPage);

				const spanNextPage = document.createElement('span');
				spanNextPage.setAttribute('aria-hidden', 'true');
				spanNextPage.innerText = '>';
				aNextPage.appendChild(spanNextPage);

				const liNext = document.createElement('li');
				liNext.classList.add('page-item');
				container.appendChild(liNext);

				const aNext = document.createElement('a');
				aNext.classList.add('page-link');
				aNext.setAttribute('aria-label', 'Next');
				aNext.href = `javascript:loadReply(${currentPage + pagePerGroup})`;
				liNext.appendChild(aNext);

				const spanNext = document.createElement('span');
				spanNext.setAttribute('aria-hidden', 'true');
				spanNext.innerText = '»';
				aNext.appendChild(spanNext);
				pagingDiv.appendChild(container);
			// data : List<Reply>
			let replyTable = "<table>";// 댓글 영역에 집어 넣을 HTML을 생성
			// 반복 $.each(반복할 뭉탱이, 뭉탱이로 할일)
			$.each(data.replyList,function(index,item) {
				//hidden 태그에 들어있는 session 정보 가져오기
				let login = $("#login").val();
				replyTable += "<tr>";
				replyTable += "<td>" + item.replyText + "</td>";
				replyTable += "<td>" + item.userId + "</td>";
				replyTable += "<td>"
				if(item.userId === login){
					replyTable += "<a href = 'javascript:getOneReply(" + item.replyNum + ");'>수정하기</a> &nbsp;";
					replyTable += "<a href = 'javascript:deleteReply(" + item.replyNum + ");'>삭제하기</a>";
				}
				replyTable += "</td>"
				replyTable += "</tr>";
				
			});

			replyTable += "</table>";
			// reList라는 id를 가진 요소에 replyTalbe에 있는 HTML 문자열을 추가하겠음
			$("#reList").html(replyTable);
			}
		});
	}
	
	function getOneReply(num){
		// 해봅시다
		// 지금까지 한 ajax 함수 참고해서 컨트롤러에서
		// /getOneReply 라는 url 호출하면 댓글 정보 돌려주는 메서드 만들어보세요
		
		$.ajax({
			
			url : "/getOneReply",
			method : "post",
			data : {"replyNum" : num},
			success : function(reply){
				// 얘는 입력창				얘는 객체에 들어있느 댓글내용
				$("#replyText").val(reply.replyText);
				
				// 댓글 달기 버튼을 숨기고
				$("#btnReply").hide();
				// 댓글 수정 버튼은 보여줘야함
				$("#btnUpdate").show();
				
				// 숨겨진 selectedReply에 value를 글번호로 설정
				$("#selectedReply").val(reply.replyNum);

			}
		});
	}
	
	function updateReply() {
		//새로운 내용 가져오기
		let newReply = $("#replyText").val();
		let rNum = $("#selectedReply").val();
		$.ajax({
			url : "/updateReply",
			method : "post",
			data : {"replyText" : newReply, "replyNum" : rNum},
			success : function(data){
				loadReply();
				$("#btnUpdate").hide();
				$("#replyText").val("");
				$("#btnReply").show();
			}
			
		});
	}
	// 댓글 삭제 링크를 클릭했을 때 파라미터에 입력된 댓글번호를 num이라는 이름으로 가져옴
	function deleteReply(rNum){
		
		if(confirm("삭제 하시겠습니까?")){
			$.ajax({
				url : "/deleteReply",
				method : "post",
				data : {"replyNum" : rNum},
				success : function(){
					
					loadReply();
				}
			});
		}
	}
