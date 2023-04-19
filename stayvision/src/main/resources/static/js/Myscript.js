function hideRows() {
    // 원하는 행의 개수
    const numRowsToShow = 3;

    // 테이블 요소 선택
    const table = document.getElementById('notice');
    const tableRows = table.rows;

    // 행의 개수가 표시할 행의 개수보다 작으면 모두 표시
    if (tableRows.length <= numRowsToShow) {
      return;
    }

    // 지정한 행 이후의 행은 숨기기
    for (let i = numRowsToShow; i < tableRows.length; i++) {
      tableRows[i].style.display = 'none';
    }
  }

  hideRows(); // 함수 호출

function hideRows2() {
    // 원하는 행의 개수
    const numRowsToShow = 4;

    // 테이블 요소 선택
    const table = document.getElementById('community');
    const tableRows = table.rows;

    // 행의 개수가 표시할 행의 개수보다 작으면 모두 표시
    if (tableRows.length <= numRowsToShow) {
      return;
    }

    // 지정한 행 이후의 행은 숨기기
    for (let i = numRowsToShow; i < tableRows.length; i++) {
      tableRows[i].style.display = 'none';
    }
  }

hideRows2(); // 함수 호출

// 로그아웃 확인창
function confirmLogout() {
    if (confirm('정말 로그아웃하시겠습니까?')) {
      window.location.href = 'logout';
    }
  }



