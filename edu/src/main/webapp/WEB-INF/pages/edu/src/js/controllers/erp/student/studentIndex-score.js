(function () {
  'use strict';

  angular
    .module('ework-ui')
    .controller('StudentScoreController', StudentScoreController);

  StudentScoreController.$inject = [
    '$scope',
    '$uibMsgbox',
    '$uibModal',
    'erp_dictService',
    'erp_studentsService',
    'erp_studentScoreService'];

  function StudentScoreController(
    $scope,
    $uibMsgbox,
    $uibModal,
    erp_dictService,
    erp_studentsService,
    erp_studentScoreService
  ) {
    var vm = this;

    $scope.studentId = $("#rootIndex_studentId").val();
    $scope.student = {};
    $scope.scoreList = [];
    $scope.examTypeList = [];
    $scope.schoolTermList = [];
    $scope.progressList = [];
    $scope.progressMap = {
      1: '进步',
      2: '退步',
      3: '持平'
    }
    $scope.pageConf = {
      currentPage: 1, //当前页
      totalItems: 0,
      itemsPerPage: 10,
      onChange: function(){
          $scope.doSearch();
      }
    }
    $scope.searchParam = {
      gradeId: null,
      // subjectId: null,
      term: null,
      examType: null
    }

    ////////////////
    // 获取列表
    $scope.doSearch = function () {
      $scope.searchParam.studentId = $scope.studentId;
      $scope.searchParam.currentPage = $scope.pageConf.currentPage;
      $scope.searchParam.pageSize = $scope.pageConf.itemsPerPage;
      erp_studentScoreService.query($scope.searchParam).$promise
        .then(function (resp) {
          if (!resp.error) {
            _.forEach(resp.data, function (item) {
              item.showDetailList = false;
            })
            $scope.scoreList = resp.data;
            $scope.pageConf.totalItems = resp.total;
          } else {
            $uibMsgbox.error(resp.message);
          }
        }, function (resp) {
          $uibMsgbox.error(resp);
        })
    }

    function openScoreDetailModal (optype, score) {
      return $uibModal.open({
        size: 'lg',
        backdrop: 'static',
        resolve: {
          optype: function () {
            return optype
          },
          score: function () {
            return score
          },
          student: function () {
            return $scope.student
          },
          schoolTermList: function () {
            return $scope.schoolTermList
          },
          examTypeList: function () {
            return $scope.examTypeList
          }
        },
        templateUrl: 'studentScoreDetail.html',
        controller: [
          '$scope', 
          '$uibMsgbox',
          'optype',
          'student',
          'examTypeList',
          'schoolTermList',
          'erp_dictService',
          'erp_studentSchoolService', 
          function (
            $scope, 
            $uibMsgbox,
            optype,
            student,
            examTypeList,
            schoolTermList,
            erp_dictService,
            erp_studentSchoolService
          ) {
            $scope.forms = {};
            $scope.optype = optype;
            $scope.student = student;
            $scope.examTypeList = examTypeList;
            $scope.schoolTermList = schoolTermList;
            $scope.school = {}
            $scope.progressList = [{
              value: '1', label: '进步'
            }, {
              value: '2', label: '退步'
            }, {
              value: '3', label: '持平'
            }];

            $scope.scoreRankingTypeList = [];

            $scope.score = {
              term: null,
              studentScoreList: [],
              gradeId: ''
            }
            if (optype == 'edit' && score) {
              $scope.score = {
                id: score.id || null,
                studentId: score.studentId || student.id,
                schoolId: score.schoolId || student.attend_school_id,
                gradeId: score.gradeId || student.gradeId,
                term: score.term || '',
                examType: score.examType || '',
                studentScoreList: score.studentScoreList || [],
                studentScoreRankingList: score.studentScoreRankingList || []
              }
            }
            $scope.getSchools = function(schoolName) {
              return erp_studentSchoolService.query({
                pageSize: 20,
                currentPage: 1,
                school_name: schoolName
              }).$promise.then(function(resp) {
                return resp.data
              })
            }
            $scope.addScoreItem = function () {
              if (!_.isArray($scope.score.studentScoreList)) {
                $scope.score.studentScoreList = []
              }
              $scope.score.studentScoreList.push({
                progress: '3',
                fullMark: 100
              })
            }

            $scope.addScoreRankingItem = function() {
              if (!_.isArray($scope.score.studentScoreRankingList)) {
                $scope.score.studentScoreRankingList = []
              }
              $scope.score.studentScoreRankingList.push({
                scoreRankingType: '',
                ranking: ''
              });
            }

            $scope.deleteScoreItem = function(idx) {
              $scope.score.studentScoreList.splice(idx, 1)
            }

            $scope.deleteScoreRankingItem = function(idx) {
              $scope.score.studentScoreRankingList.splice(idx, 1);
            }

            function valid () {
              if (!$scope.forms.score.$valid) {
                $uibMsgbox.error('请校验输入是否正确，带*号的必填荐是否都有填写！')
                return false
              }
              if(_.isArray($scope.score.studentScoreList)) {
                for (var i = 0; i < $scope.score.studentScoreList.length; i ++ ) {
                  var item = $scope.score.studentScoreList[i]
                  var prefix = '序号为【' + (i + 1) +'】的单科成绩信息校验失败：'
                  if (!item.subjectId) {
                    $uibMsgbox.error( prefix + '未选择【科目】，请选择【科目】！')
                    return false
                  }
                  if (!item.score || !_.isNumber(+item.score) || item.score < 0) {
                    $uibMsgbox.error(prefix + '【成绩】输入不合法！')
                    return false
                  }
                  if (!item.fullMark || !_.isNumber(+item.fullMark) || item.score < 0) {
                    $uibMsgbox.error(prefix + '【满分】分数输入不合法！')
                    return false
                  }
                  if (item.fullMark < item.score) {
                    $uibMsgbox.error(prefix + '【成绩】不能大于【满分】成绩！')
                    return false
                  }
                }
              } else {
                $uibMsgbox.error('请至少输入一条学生单科成绩！')
                return false
              }
              return true
            }
            $scope.onOk = function () {
              if (!valid()) {
                return false;
              }
              $scope.score.studentId = student.id
              $scope.score.schoolId = $scope.school.id

              var serviceRet = null
              if (optype == 'add') {
                serviceRet = erp_studentScoreService.post($scope.score)
              } else {
                serviceRet = erp_studentScoreService.put($scope.score)
              }
              var waitingModal = $uibMsgbox.waiting('保存中，请稍候！');
              serviceRet.$promise.then(function (resp) {
                waitingModal.close();
                if (!resp.error) {
                  $scope.$close()
                } else {
                  $uibMsgbox.error(resp.message)
                }
              }, function (resp) {
                waitingModal.close();
                $uibMsgbox.error('请求失败：' + resp)
                console.log(resp)
              })
            }
            
            init()
            ///////

            function init() {
              if (!$scope.score.gradeId && student.grade_id) {
                $scope.score.gradeId = student.grade_id
              }
              if (student.attend_school_name && student.attend_school_id) {
                $scope.school = {
                  id: student.attend_school_id,
                  school_name: student.attend_school_name
                }
              }

              queryScoreRankingTypeList ();
            }
            
            function queryScoreRankingTypeList () {
              return erp_dictService.get({
                code: 'scoreRankingType'
              }).$promise.then(function (resp) {
                if (!resp.error) {
                  $scope.scoreRankingTypeList = resp.data;
                } else {
                  $uibMsgbox.error(resp.message);
                }
              }, function (resp) {
                $uibMsgbox.error(resp)
              })
            }
          }]
      })
    }
    $scope.handleAddScore = function () {
      openScoreDetailModal('add').result.then(function() {
        $scope.doSearch()
      }, function () {})
    }

    $scope.handleEditScore = function (score) {
      openScoreDetailModal('edit', score).result.then(function() {
        $scope.doSearch()
      }, function () {})
    }

    $scope.handleDeleteScore = function (score) {
      $uibMsgbox.confirm('确定删除所选成绩档案？', function(res) {
        if (res == 'yes') {
          erp_studentScoreService.delete({
            id: score.id
          }).$promise.then(function(resp) {
            if (!resp.error) {
              $scope.doSearch()
            } else {
              $uibMsgbox.error(resp.message)
              $scope.doSearch()
            }
          })
        }
      })
    }

    function queryStudentInfo () {
      return erp_studentsService.query({
        row_num: 20,
        studentId: $scope.studentId
      }).$promise.then(function(resp) {
        if (!resp.error && resp.data.length) {
          $scope.student = resp.data[0];
        } else {
          $uibMsgbox.error('查询不到学生信息！');
        }
      }, function () {});
    }

    // 获取学期列表
    function querySchoolTermList () {
      return erp_dictService.get({
        code: 'term'
      }).$promise.then(function (resp) {
        if (!resp.error) {
          $scope.schoolTermList = resp.data;
        } else {
          $uibMsgbox.error(resp.message);
        }
      }, function (resp) {
        $uibMsgbox.error(resp);
      })
    }

    // 获取考试类型列表
    function queryExamTypeList () {
      return erp_dictService.get({
        code: 'examType'
      }).$promise.then(function (resp) {
        if (!resp.error) {
          $scope.examTypeList = resp.data;
        } else {
          $uibMsgbox.error(resp.message);
        }
      }, function (resp) {
        $uibMsgbox.error(resp)
      })
    }

    // 获取学生进步情况列表
    function queryStuProgressList () {
      return erp_dictService.get({
        code: 'progress'
      }).$promise.then(function (resp) {
        if (!resp.error) {
          $scope.progressList = resp.data;
        } else {
          $uibMsgbox.error(resp.message);
        }
      }, function (resp) {
        $uibMsgbox.error(resp)
      })
    }

    

    //////////////////
    activate();

    function activate() {
      // 获取学生信息
      queryStudentInfo().then(function () {
        $scope.searchParam.gradeId = $scope.student.grade_id
        return querySchoolTermList()
      }).then(function () {
        return queryExamTypeList()
      }).then(function () {
        return queryStuProgressList()
      }).then(function () {
        $scope.doSearch()
      })
    }
  }
})();