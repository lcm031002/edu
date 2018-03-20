(function() {
    'use strict';

    angular
        .module('ework-ui')
        .controller('erp_arrangerEditController', erp_arrangerEditController);

    erp_arrangerEditController.$inject = [
        '$scope',
        '$state',
        '$stateParams',
        '$uibModal',
        '$uibMsgbox',
        'erp_arrangerService'
    ];

    function erp_arrangerEditController(
        $scope,
        $state,
        $stateParams,
        $uibModal,
        $uibMsgbox,
        erp_arrangerService
    ) {
        var vm = this;

        // panel title, default: 排课专员
        $scope.panelTitle = '排课专员';

        // arranger detail
        $scope.arranger = {
            id: null,
            employeeId: null,
            employeeName: null,
            buId: null,
            buName: null,
            courseArrangeSpSubjectList: []
        }

        // Go back to arrange list page
        $scope.goBack = function() {
            $state.go('systemDataArranger')
        }

        // Get Arranger Detail
        $scope.getArrangerDetail = function(id) {
            erp_arrangerService.getDetail({
                id: id
            }).$promise.then(function(resp) {
                if (!resp.error) {
                    $scope.arranger = resp.data;
                } else {
                    $uibMsgbox.error(resp.message + ' 点击确定，返回排课专员列表',
                        function(res) {
                            $scope.goBack();
                        }
                    );
                }
            }, function(resp) {
                $uibMsgbox.error('请求数据失败！请联系管理员！', function(res) {
                    $scope.goBack();
                })
            })
        }
        $scope.onRemoveSubject = function(index) {
            $uibMsgbox.confirm('确定删除所选科目?', function(res) {
                if (res == 'yes') {
                    $scope.arranger.courseArrangeSpSubjectList.splice(index, 1);
                }
            })
        }

        $scope.onAddSubject = function() {
            openSubjectDetailModal('add', {})
                .then(function(subject) {
                    var tag = true;
                    $scope.arranger.courseArrangeSpSubjectList.forEach(function(e) {
                        if (e.subjectId == subject.subjectId) {
                            tag = false
                        }
                    });
                    if (tag) {
                        $scope.arranger.courseArrangeSpSubjectList.push(subject)
                    } else {
                        $uibMsgbox.warn('已存在该科目，请勿重复添加');
                    }
                }, function() {})
        }

        $scope.onEditSubject = function(index, subject) {
            openSubjectDetailModal('edit', subject)
                .then(function(item) {
                    subject = _.cloneDeep(item)
                    $scope.arranger.courseArrangeSpSubjectList.splice(index, 1, subject)
                }, function() {})
        }

        $scope.onSave = function() {
            if (!arrangerValid($scope.arranger)) {
                return false
            }
            var saveRes = null;
            if ($stateParams.optype == 'edit') {
                saveRes = erp_arrangerService.put(genSavedArranger($scope.arranger)).$promise;
            } else if ($stateParams.optype == 'add') {
                saveRes = erp_arrangerService.post(genSavedArranger($scope.arranger)).$promise;
            }
            if (saveRes) {
                var waitModal = $uibMsgbox.waiting('保存中，请稍候...');
                saveRes.then(function(resp) {
                    waitModal.close();
                    if (!resp.error) {
                        $uibMsgbox.success('保存成功！', function(res) {
                            $scope.goBack();
                        })
                    } else {
                        $uibMsgbox.error(resp.message || '保存失败！请重新保存或者联系管理员！')
                    }
                }, function(resp) {
                    waitModal.close();
                    console.error(resp);
                    $uibMsgbox.error('请求失败！请重新保存或者联系管理员！');
                })
            }
        }

        $scope.onCancel = function() {
            $uibMsgbox.confirm('排课专员信息还未保存，返回之后将不做任何修改，确定返回？', function(res) {
                if (res == 'yes') {
                    $scope.goBack()
                }
            })
        }
        activate();

        ////////////////
        function genSavedArranger(arranger) {
            var data = _.pick(arranger, ['id', 'employeeId', 'employeeName', 'buId'])
            data.courseArrangeSpSubjectList = []
            _.forEach(arranger.courseArrangeSpSubjectList, function(item) {
                var subjectDetail = _.pick(item, ['subjectId', 'subjectName'])
                if (!subjectDetail) {
                    return
                }
                subjectDetail.courseArrangeSpGradeList = []
                _.forEach(item.courseArrangeSpGradeList, function(grade) {
                    subjectDetail.courseArrangeSpGradeList.push({
                        gradeId: grade.gradeId
                    })
                })

                data.courseArrangeSpSubjectList.push(subjectDetail)
            })
            return data
        }

        function arrangerValid(arranger) {
            if (!arranger.employeeId) {
                $uibMsgbox.error('请选择关联员工！');
                return false;
            }
            if (!arranger.buId) {
                $uibMsgbox.error('请选择所属团队！');
                return false;
            }
            if (!arranger.courseArrangeSpSubjectList || arranger.courseArrangeSpSubjectList.length == 0) {
                $uibMsgbox.error('请至少选择一个科目！');
                return false;
            }
            return true;
        }

        function openSubjectDetailModal(optype, subjectDetail) {
            return $uibModal.open({
                backdrop: 'static',
                templateUrl: 'subjectModal.html',
                resolve: {
                    optype: function() {
                        return optype
                    },
                    subject: function() {
                        return _.cloneDeep(subjectDetail)
                    }
                },
                controller: ['$scope', '$uibMsgbox', 'optype', 'subject', 'erp_gradeService', function(
                    $scope, $uibMsgbox, optype, subject, erp_gradeService
                ) {
                    $scope.subject = subject
                    $scope.subject.courseArrangeSpGradeList = $scope.subject.courseArrangeSpGradeList || []
                    $scope.gradeList = []
                    $scope.gradeSearchInfo = ''

                    $scope.getSubjectList = function() {
                        erp_gradeService.query({
                            pageSize: 999
                        }).$promise.then(function(resp) {
                            if (!resp.error) {
                                var gradeList = []
                                _.forEach(resp.data, function(item) {
                                    gradeList.push({
                                        checked: false,
                                        gradeId: item.id,
                                        gradeName: item.grade_name,
                                        gradeEncodeing: item.encoding
                                    })
                                })
                                handleCheckedGrade(gradeList, $scope.subject.courseArrangeSpGradeList)
                                $scope.gradeList = gradeList
                            } else {
                                console.error(resp)
                            }
                        }, function(resp) {
                            console.error(resp)
                        })
                    }

                    $scope.handleRemoveGrade = function(grade) {
                        removeGrade($scope.subject.courseArrangeSpGradeList, grade);
                        handleCheckedGrade($scope.gradeList, $scope.subject.courseArrangeSpGradeList);
                    }

                    $scope.handleGradeChange = function(grade) {
                        if (grade.checked) {
                            insertGrade($scope.subject.courseArrangeSpGradeList, grade);
                        } else {
                            removeGrade($scope.subject.courseArrangeSpGradeList, grade);
                        }
                    }

                    $scope.onSave = function() {
                        if (!$scope.subject.subjectId) {
                            return $uibMsgbox.error('请选择科目！');
                        }
                        if (!$scope.subject.courseArrangeSpGradeList || $scope.subject.courseArrangeSpGradeList.length == 0) {
                            return $uibMsgbox.error('请至少选择一个年级！');
                        }
                        $scope.$close($scope.subject);
                    }
                    activate()

                    ////////
                    function handleCheckedGrade(gradeList, checkedGradeList) {
                        _.forEach(gradeList, function(item) { item.checked = false })

                        var intersectionList = _.intersectionBy(gradeList, checkedGradeList, 'gradeId')

                        _.forEach(intersectionList, function(item) {
                            item.checked = true
                        })
                    }

                    function insertGrade(gradeList, grade) {
                        if (_.findIndex(gradeList, { gradeId: grade.gradeId }) == -1) {
                            gradeList.push(_.cloneDeep(grade))
                        }
                    }

                    function removeGrade(gradeList, grade) {
                        var index = _.findIndex(gradeList, { gradeId: grade.gradeId })
                        if (index != -1) {
                            gradeList.splice(index, 1)
                        }
                    }

                    function activate() {
                        $scope.getSubjectList();
                    }
                }]

            }).result
        }

        function activate() {
            // process router params
            if ($stateParams.optype == 'add') {
                $scope.panelTitle = '新增排课专员';
            } else if ($stateParams.optype == 'edit' && $stateParams.id) {
                $scope.panelTitle = '编辑排课专员';
                $scope.getArrangerDetail($stateParams.id);
            } else {
                return $scope.goBack()
            }
        }
    }
})();