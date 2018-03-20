(function () {
  'use strict';

  // Usage:
  // 
  // Creates:
  // 

  angular.module('ework-ui').component('klcErpCourseStuSchedBaseInfoAnalysis', {
    //template:'htmlTemplate',
    templateUrl: 'templates/components/erp/course/stuSchedBaseInfoAnalysis.html',
    controllerAs: '$ctrl',
    bindings: {
      // 申请单据
      apply: '=',
      readOnly: '=?'
    },
    controller: [
      '$scope',
      '$uibMsgbox',
      'erp_dictService',
      'erp_studentScoreService',
      function (
        $scope, 
        $uibMsgbox,
        erp_dictService,
        erp_studentScoreService
      ) {
        var $ctrl = this;
        $scope.apply = $ctrl.apply;

        $scope.termList = [];
        $scope.examTypeList = [];
        $scope.latestScore = {};
        $scope.applyTypeList = [{
          code: "1",
          name: '新单'
        }, {
          code: "2",
          name: '加课单'
        }, {
          code: "3",
          name: '换单'
        }];
        ////////////////

        $ctrl.$onInit = function () {
          getDictionary($scope.termList, 'term');
          getDictionary($scope.examTypeList, 'examType');
          $scope.$watch(function() {return $ctrl.apply}, function () {
            $scope.apply = $ctrl.apply;
          })
        };

        $ctrl.$onDestroy = function () {
        };

        $scope.$watch('apply.studentId', function (newValue, oldValue) {
          if (newValue) {
            $scope.getLatestScore();
          }
        });

        $scope.getLatestScore = function () {
          erp_studentScoreService.query({
            studentId: $scope.apply.studentId,
            gradeId: $scope.apply.gradeId,
            term: $scope.apply.term,
            examType: $scope.apply.examType
          }).$promise.then(function (resp) {
            if (!resp.error) {
              var score = {}
              if (_.isArray(resp.data) && resp.data.length >= 1) {
                 score = resp.data[0]
              }
              // $scope.apply.gradeId = score.gradeId;
              // $scope.apply.gradeName = score.gradeName;
              // $scope.apply.term = score.term;
              // $scope.apply.studentRanking = score.ranking || null;
              $scope.apply.stuScoreList = score.studentScoreList || [];
              $scope.apply.stuScoreRankingList = score.studentScoreRankingList || [];
            } else {
              $uibMsgbox.error(resp.message)
            }
          })
        }

        // 获取字典数据
        function getDictionary(listRef, code) {
          return erp_dictService.get({
            code: code
          }).$promise.then(function (resp) {
            if (!resp.error) {
              _.forEach(resp.data, function (item) {
                listRef.push(item);
              })
            } else {
              $uibMsgbox.error(resp.message)
            }
          }, function (resp) {return false;})
        }
      }
    ]
  });
})();

/*****
// 打开学生成绩对话框
function openScoreModal (applyId, score, optype) {
  var _score = _.cloneDeep(score)
  return $uibModal.open({
    templateUrl: 'erp_courseSchedApplyScoreModal.html',
    size: 'sm',
    resolve: {
      applyId: function () {
        return applyId
      },
      score: function() {
        return _score || {}
      },
      optype: function () {
        return optype || 'add'
      }
    },
    controller: [
      '$scope',
      'erp_stuCourseSchedApplyYdyService',
      'applyId',
      'score',
      'optype',
      function (
        $scope,
        erp_stuCourseSchedApplyYdyService,
        applyId, 
        score, 
        optype
      ) {
        $scope.score = score || {}
        if (optype == 'add') {
          $scope.score.fullMark = $scope.score.fullMark || 100
        }
        $scope.saveScore = function () {
          $scope.score.applyId = applyId
          $scope.score.subjectId = parseInt($scope.score.subjectId, 10)
          $scope.score.score = parseInt($scope.score.score, 10)
          $scope.score.fullMark = parseInt($scope.score.fullMark, 10)
          if (!$scope.score.subjectId) {
            return $uibMsgbox.warn('请选择科目！')
          }
          if (!$scope.score.score || isNaN($scope.score.score)) {
            return $uibMsgbox.warn('请输入成绩!')
          }
          if (!$scope.score.fullMark || isNaN($scope.score.fullMark)) {
            return $uibMsgbox.warn('请输入满分分数')
          }
          if ($scope.score.score < 0 || $scope.score.score > $scope.score.fullMark) {
              return $uibMsgbox.warn('输入的课程分数必须在0到' + $scope.score.fullMark + '之间！')
          }
          var commitFunc = erp_stuCourseSchedApplyYdyService.postScore
          if (optype == 'edit') {
            commitFunc = erp_stuCourseSchedApplyYdyService.putScore
          }
          commitFunc($scope.score).$promise.then(function (resp) {
            if (!resp.error) {
              if (optype == 'edit') {
                $scope.$close($scope.score)
              } else {
                $scope.$close(_.pick(resp.data, ['id', 'applyId', 'score', 'fullMark', 'subjectId', 'subjectName']))
              }
            } else {
              $uibMsgbox.error(resp.message)
            }
          })
        }
      }
    ]
  })
} 
// 添加学生近期成绩
  $scope.addScore = function () {
    var modalInstance = openScoreModal($scope.apply.id)
    modalInstance.result.then(function (score) {
      $scope.apply.stuScoreList.push(score)
    }, function () {})
  }
  // 编辑学生近期成绩
  $scope.editScore = function (score) {
    var modalInstance = openScoreModal($scope.apply.id, score, 'edit')
    modalInstance.result.then(function (editScore) {
      score = _.assign(score, editScore)
    }, function () {})
  }
  $scope.delScore = function (score) {
    $uibMsgbox.confirm('确定删除所选科目的成绩？', function (res) {
      if (res == 'yes') {
        erp_stuCourseSchedApplyYdyService.delScore(score, function (resp) {
          if (!resp.error) {
            $uibMsgbox.success('删除成功')
            $scope.apply.stuScoreList.splice(
              _.findIndex($scope.apply.stuScoreList, score), 1
            )
          }
        })
      }
    })
  }
*/