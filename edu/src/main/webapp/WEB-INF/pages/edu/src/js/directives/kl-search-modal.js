angular.module('ework-ui')
  .directive('klSearchModal',[klSearchModal])

function klSearchModal() {
  return {
    restrict: 'EA',
    scope: {
      klShowModal: '=klShowModal',
      klModalTitle: '=klModalTitle'
    },
    template: 
      '<div class="search-panel"> '
      +'<div class="content"> '
        +'<div class="heading"> '
          +'<div class="panel-title text-center">教师搜索</div> '
        +'</div> '
        +'<div class="body"> '
          +'<div class="form"> '
            +'<div class="input-group"> '
              +'<input type="text" class="form-control"> '
              +'<span class="input-group-btn"> '
                +'<div class="btn btn-default"><i class="fa fa-search"></i></div> '
              +'</span> '
            +'</div> '
          +'</div> '
        +'</div> '
        +'<div class="body">'
        +'</div> '
      +'</div> '
    +'</div> '
  }
}