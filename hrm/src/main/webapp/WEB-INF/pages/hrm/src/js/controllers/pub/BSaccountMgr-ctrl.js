/**
 * Created by Liyong.zhu on 2016/6/26.
 */
angular.module('ework-ui')
    .controller('BSAccountMgrCtrl', [
        '$scope',
        '$log',
        '$state',
        'BranchsService',
        'CompanyService',
        'ProductService',
        'PUBStudentAccountChangeService',
        'PUBStudentAccountTransferService',
        'PUBStudentAccountWithDrawService',
        'PosService',
        'CompanyAccountService',
        'StudentAccountService',
        'StudentQueryService',
        BSAccountMgrCtrl]);


function BSAccountMgrCtrl(
    $scope,
    $log,
    $state,
    BranchsService,
    CompanyService,
    ProductService,
    PUBStudentAccountChangeService,
    PUBStudentAccountTransferService,
    PUBStudentAccountWithDrawService,
    PosService,
    CompanyAccountService,
    StudentAccountService,
    StudentQueryService
    ){
    //支付
    $scope.pay = {
        total:0
    };
    var ids = 0;

    function genIds(){
        return ++ids;
    }

    function genSelectedStudent(){

    }

    /**
     * 添加支付
     * @param type
     */
    $scope.addPay = function(type){
        if(type == 'cash'){
            $scope.pay.type = 'cash';
            var param = {};
            param.type = '1';
            param.value = $scope.pay.value;
            param.remark = $scope.pay.remark;
            param.typeName = '现金';
            param.studentId = $scope.curStudent.id;
            param.student_encoding = $scope.curStudent.studentEncoding;
            param.student_name = $scope.curStudent.studentName;
            param.id = genIds();
            if(!$scope.pay.payList){
                $scope.pay.payList = [];
            }
            $scope.pay.payList.push(param);
            $scope.pay.total = 0;
            $.each($scope.pay.payList,function(i,m){
                $scope.pay.total+= parseInt(m.value);
            });
        }else if(type == 'card'){
            var param = {};
            param.type = '2';
            param.id = genIds();
            param.typeName = '刷卡';
            param.value = $scope.pay.value;
            param.remark = $scope.pay.remark;
            param.companyAccount = $scope.pay.companyAccount;
            param.companyCard = $scope.pay.companyCard;
            param.card = $scope.pay.card;
            param.posName = $scope.pay.posName;
            param.posId = $scope.pay.posId;

            param.remark = $scope.pay.remark;
            param.studentId = $scope.curStudent.id;
            param.student_encoding = $scope.curStudent.studentEncoding;
            param.student_name = $scope.curStudent.studentName;
            if(!$scope.pay.payList){
                $scope.pay.payList = [];
            }
            $scope.pay.payList.push(param);
            $scope.pay.total = 0;
            $.each($scope.pay.payList,function(i,m){
                $scope.pay.total+= parseInt(m.value);
            });
        }else if(type == 'transfer'){
            var param = {};
            param.type = '3';
            param.id = genIds();
            param.typeName = 'transfer';
            param.value = $scope.pay.value;
            param.remark = $scope.pay.remark;
            param.card =  $scope.pay.card;
            param.companyAccount = $scope.pay.companyAccount;
            param.companyCard = $scope.pay.companyCard;
            param.remark = $scope.pay.remark;
            param.studentId = $scope.curStudent.id;
            param.student_encoding = $scope.curStudent.studentEncoding;
            param.student_name = $scope.curStudent.studentName;
            if(!$scope.pay.payList){
                $scope.pay.payList = [];
            }
            $scope.pay.payList.push(param);
            $scope.pay.total = 0;
            $.each($scope.pay.payList,function(i,m){
                $scope.pay.total+= parseInt(m.value);
            });
        }
    }

    /**
     * 移除支付记录
     * @param type
     * @param pay
     */
    $scope.removePay = function(type,pay){
        var payList = [];
        $.each($scope.pay.payList,function(i,m){
            if(m.id != pay.id){
                payList.push(m);
            }
        });
        $scope.pay.payList = payList;

        $scope.pay.total = 0;
        $.each($scope.pay.payList,function(i,m){
            $scope.pay.total+= parseInt(m.value);
        });

    }

    /**
     * 确认支付
     * @param type
     */
    $scope.confirmPay = function(type){
    	var pay = $scope.pay;
        if(type == 'cash'){
            PUBStudentAccountChangeService.saveForCash(pay,function(resp){
                    $("#studentAccountCashRechargePanel").modal("hide");
                    $("#studentAccountRechargePanel").modal("hide");
                    alert("充值成功!");
            },function(err){
            	$log.log(err);
            	alert('系统现金缴费异常，请联系管理员，异常信息：' + err.data.message);
            	
            })
        }
        if(type == 'card'){
            PUBStudentAccountChangeService.saveForCard(pay,function(resp){
                    $("#studentAccountRechargeCardPanel").modal("hide");
                    $("#studentAccountRechargePanel").modal("hide");
                    alert("充值成功!");
            },function(err){
            	 alert("系统刷卡充值异常，请联系管理员,异常信息："+err.data.message);
            	
            })
        }
        if(type == 'transfer'){
            PUBStudentAccountChangeService.saveForTransfer(pay,function(resp){
                    $("#studentAccountTransferPanel").modal("hide");
                    $("#studentAccountRechargePanel").modal("hide");
                    alert("充值成功!");
            },function(err){
            	alert("系统转账充值异常，请联系管理员，异常信息：" + err.data.message);
            })
        }
    }
    $scope.studentListPage = {
        pageSize:10,
        currentPage:1,
        totalPage:1
    }
    
    $scope.student_searchInfo=null;
    /**
     * 搜索学员
     */
    $scope.queryStudentList = function(){
        var param = {};
        param.searchInfo = $("#student_search_info").val();
        $scope.curStudent = null;
        $scope.studentList = [];
        StudentQueryService.query(param,function(resp){
        	if(resp.error=='false'){
        		$scope.studentList = resp.data;
        	}else{
        		alert('搜索学生信息异常，请联系管理员，异常信息：'+resp.message);
        	}
        });
    }

    $scope.queryStudentList();
    
    //当前的学员
    $scope.curStudent = null;

    /**
     * 获取已经选择的学员
     */
    $scope.checkedStudent = function(student){
        if(student.checked){
            student.checked = false;
            if($scope.curStudent&&$scope.curStudent.id == student.id){
                $scope.curStudent = null;
            }
        }else{
            if($scope.curStudent&&$scope.curStudent.id != student.id){
                $scope.curStudent.checked = false;
            }
            student.checked = true;
            $scope.curStudent = student;
        }
    }

    //类型
    $scope.type = '';

    //////////////////////////////////////////////////////////////////////////////////////begin:转账///////////////////////////////////////////////////////////////////////////////////////////
    //学员信息
    $scope.studentTransferAcount = {
        transferInStudent:{
            studentId:"",
            studentEncoding:"",
            studentName:"",
            studentType:"",
            studentAccount:"",
            studentAccountAfter:""
        },
        transferOutStudent:{
            studentId:"",
            studentEncoding:"",
            studentName:"",
            studentType:"",
            studentAccount:"",
            studentAccountAfter:""
        },
        transferAccount:0,
        transferRemark:''
    };

    $scope.studentList = [];
    
    /**
     * 打开充值的面板
     * @param type
     */
    $scope.toOpenAccountRechargePanel = function(type){
        $scope.curStudent = null;
        $scope.type = type;
        $("#studentSelectedPanel").modal("show");
    }

    /**
     * 学员搜索
     */
    $scope.studentSearch = function(student,type){
        student.student_list_name = undefined;
        student.student_list_encoding = undefined;
        if(type == 'name'){
            var param = {};
            param.searchInfo = student.studentName;
            param.currentPage = 1;
            param.pageSize = 100;
            StudentQueryService.query(param,function(resp){
                if(resp.error == 'false'){
                    student.student_list_name = resp.data;
                }
            });

        } else if(type == 'encoding'){
            var param = {};
            param.searchInfo = student.studentEncoding;
            param.currentPage = 1;
            param.pageSize = 100;
            StudentQueryService.query(param,function(resp){
                if(resp.error == 'false'){
                    student.student_list_encoding = resp.data;
                }
            });
        }
    }

    /**
     * 选择学员
     * @param studentObj
     * @param selectStudent
     */
    $scope.selectStudent = function(studentObj,selectStudent){
        studentObj.student_list_name = null;
        studentObj.student_list_encoding = null;
        studentObj.studentEncoding = selectStudent.studentEncoding;
        studentObj.studentName = selectStudent.studentName;
        studentObj.studentId = selectStudent.id;
        studentObj.studentAccountType =  $scope.productList[0].id;
        $scope.checkedAccountType(studentObj,$scope.productList[0]);
    }

    $scope.parseInt = function(v){
        return parseInt(v);
    }

    /**
     * 选择账户类型
     * @param studentObj
     * @param product
     */
    $scope.checkedAccountType = function(studentObj,product){
        studentObj.studentAccountType =  product.id;
        var param = {};
        param.productLine = product.id;
        param.studentId = studentObj.studentId;
        StudentAccountService.query(param,function(resp){
            if(resp.error == 'false'){
            	studentObj.studentAccount = resp.data.fee_amount;
            }
        });
    }


    /**
     * 打开学生转账面板
     */
    $scope.studentTransferStudentAccountPanel = function(){
        $scope.studentTransferAcount = {
            transferInStudent:{
                studentEncoding:"",
                studentName:"",
                studentAccount:{},
                studentAccountAfter:{}
            },
            transferOutStudent:{
                studentEncoding:"",
                studentName:"",
                studentAccount:{},
                studentAccountAfter:{}
            },
            transferAccount:0,
            transferRemark:''
        };


        $("#studentTransferStudentAccountPanel").modal("show");
    }

    $scope.transferAccountChange = function(){
        if($scope.studentTransferAcount.transferAccount>$scope.studentTransferAcount.transferInStudent.studentAccount[$scope.studentTransferAcount.transferInStudent.studentAccountType]){
            $scope.studentTransferAcount.transferAccount = $scope.studentTransferAcount.transferInStudent.studentAccount[$scope.studentTransferAcount.transferInStudent.studentAccountType]
        }
    }
    /**
     * 确认提交转账
     */
    $scope.confirmTransferAccount = function(){

        var param = {
            transferInStudent:{
                studentEncoding:$scope.studentTransferAcount.transferInStudent.studentEncoding,
                studentName:$scope.studentTransferAcount.transferInStudent.studentName,
                studentAccountType:$scope.studentTransferAcount.transferInStudent.studentAccountType,
                studentAccount:$scope.studentTransferAcount.transferInStudent.studentAccount,
                studentAccountAfter:$scope.studentTransferAcount.transferInStudent.studentAccountAfter
            },
            transferOutStudent:{
                studentEncoding:$scope.studentTransferAcount.transferOutStudent.studentEncoding,
                studentName:$scope.studentTransferAcount.transferOutStudent.studentName,
                studentAccountType:$scope.studentTransferAcount.transferOutStudent.studentAccountType,
                studentAccount:$scope.studentTransferAcount.transferOutStudent.studentAccount,
                studentAccountAfter:$scope.studentTransferAcount.transferOutStudent.studentAccountAfter
            },
            transferAccount:$scope.studentTransferAcount.transferAccount,
            transferRemark:$scope.studentTransferAcount.transferRemark
        };
        PUBStudentAccountTransferService.post(param,function(resp){
            if(resp.error == 'false'){
                $scope.studentTransferAcount = null;
                    $("#studentTransferStudentAccountPanel").modal("hide");
                alert("提交成功！");
            }
        })
    }

    ////////////////////////////////////////////////////////////////////////////////////////end:转账//////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////begin:取款/////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 打开取款面板
     */
    $scope.openWithdrawPanel = function(){
        $scope.withDrawStudent = {
            studentId:"",
            studentEncoding:"",
            studentName:"",
            studentCard:"",
            studentAmount:"",
            studentCounterFee:"",
            studentRemark:""
        };
        $("#studentAccountWithdrawPanel").modal("show");
    }
    /**
     * 学员信息对象定义
     * @type {{studentId: string, studentEncoding: string, studentName: string, studentCard: string, studentAmount: string, studentCounterFee: string, studentRemark: string}}
     */
    $scope.withDrawStudent = {
        studentId:"",
        studentEncoding:"",
        studentName:"",
        studentCard:"",
        studentAmount:"",
        studentCounterFee:"",
        studentRemark:""
    };
    /**
     * 确认取款
     */
    $scope.confirmWithDraw = function(){
        var param = {};
        param.studentId = $scope.withDrawStudent.studentId;
        param.studentEncoding = $scope.withDrawStudent.studentEncoding;
        param.studentName = $scope.withDrawStudent.studentName;
        param.studentCard = $scope.withDrawStudent.studentCard;
        param.money = $scope.withDrawStudent.studentAmount;
        param.moneyFee = $scope.withDrawStudent.studentCounterFee;
        param.remark = $scope.withDrawStudent.studentRemark;
        param.productLine = $scope.withDrawStudent.studentAccountType;

        PUBStudentAccountWithDrawService.withDraw(param,function(resp){
                $("#studentAccountWithdrawPanel").modal("hide");
                alert('保存成功！');
        },function(err){
        	  alert("取款功能异常，请联系管理员！异常信息：" + err.data.message);
        })
    }



    ////////////////////////////////////////////////////////////////////////////////////////end:取款///////////////////////////////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////////////////////////begin:理赔///////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 理赔参数对象，注意：影藏参数入，产品线类型，以及账户余额
     * @type {{studentId: string, studentEncoding: string, studentName: string, studentAmount: string, studentRemark: string}}
     */
    $scope.settlementOfClaims = {
        studentId:"",
        studentEncoding:"",
        studentName:"",
        studentAmount:"",
        studentRemark:""
    }
    /**
     * 打开理赔的工作面板
     */
    $scope.openSettlementOfClaims = function(){
        $scope.settlementOfClaims = {
            studentId:"",
            studentEncoding:"",
            studentName:"",
            studentAmount:"",
            studentRemark:""
        }
        $("#studentAccountSettlementOfClaimsPanel").modal("show");
    }

    /**
     * 确认取款
     */
    $scope.confirmSettlementOfClaims = function(){
        var param = {};
        param.studentId = $scope.settlementOfClaims.studentId;
        param.studentEncoding = $scope.settlementOfClaims.studentEncoding;
        param.studentName = $scope.settlementOfClaims.studentName;
        param.studentAmount = $scope.settlementOfClaims.studentAmount;
        param.studentRemark = $scope.settlementOfClaims.studentRemark;
        param.studentAccountType = $scope.settlementOfClaims.studentAccountType;

        PUBStudentAccountWithDrawService.get(param,function(resp){
            if(resp.error == 'false'){
                $("#studentAccountWithdrawPanel").modal("hide");
                alert('保存成功！');
            }
        })
    }

    ///////////////////////////////////////////////////////////////////////////////////////end:理赔//////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 去充值
     */
    $scope.toAccountRechargePanel = function(type){
        $scope.type = type;
        if(!$scope.curStudent){
            $("#studentSelectedPanel").modal("show");
            return;
        }
        $("#studentSelectedPanel").modal("hide");
        $("#studentAccountRechargePanel").modal("hide");
        if(type=='cash'){

            $scope.pay = {
                total:0
            };
            $("#studentAccountCashRechargePanel").modal("show");
        }
        else if(type=='card'){
            $scope.pay = {
                total:0,
                posList:$scope.posList
            };
            $("#studentAccountRechargeCardPanel").modal("show");
        }
        else if(type == 'transfer'){
            $scope.pay = {
                total:0,
                accountList:$scope.accountList
            };
            $("#studentAccountTransferPanel").modal("show");
        }
        else{
            $("#studentAccountRechargePanel").modal("show");
        }
    }

    /**
     * 查询信息
     */
    $scope.queryInfo = function(){
    	$scope.currentPage=1;
        queryPUBStudentAccountChange();
    }

    /**
     * 选择pos机
     * @param pos
     */
    $scope.selectedPos = function(pos){
        $scope.pay.companyAccount = pos.accountName;
        $scope.pay.companyCard = pos.companyCardId;
        $scope.pay.posName = pos.name;
        $scope.pay.posId = pos.id;
    }

    /**
     * 选择公司账户
     * @param account
     */
    $scope.selectedAccount = function(account){
        $scope.pay.companyAccount = account.name;
        $scope.pay.companyCard = account.id;
    }

    /**
     * 对公学生账户流水变动表
     */
    function queryPUBStudentAccountChange(){
        var param = {};
        param.rows = 6; //页数
        param.pageSize = $scope.pageSize;
        param.currentPage = $scope.currentPage;
        param.totalPage = $scope.totalPage;
        param.searchInfo = $scope.searchInfo;
        if($scope.selectedCompany){
            param.companyId =  $scope.selectedCompany.id;
        }
        if($scope.selectedBranch){
            param.schoolId =  $scope.selectedBranch.id;
        }
        $scope.isLoadingOrders = 'loading...';
        $scope.accountChangeList=[];
        PUBStudentAccountChangeService.query(param,function(resp){
                $scope.isLoadingOrders = '';
                $scope.accountChangeList = resp.data;
                $scope.currentPage = resp.currentPage;
                $scope.totalPage = resp.totalPage;
                $scope.pageSize = resp.pageSize;
                changeNumber();
        },function(err){
            alert('查询账户异常,请联系管理员！异常信息：' + err.data.message);        	
        });
    }

    function queryPos(){
        PosService.query({},function(resp){
            if(resp.error == 'false'){
                $scope.posList = resp.data;
            }
        })
    }

    function queryCompanyAccount(){
        CompanyAccountService.query({},function(resp){
            if(resp.error == 'false'){
                $scope.accountList = resp.data;
            }
        })
    }

    function queryProduct(){
        ProductService.query({},function(resp){
            if(resp.error == 'false'){
                $scope.productList = resp.data;
            }
        })
    }
    
    /**
     * 查询归属公司的可见校区列表
     */
    function queryBranchs(){
        BranchsService.get({},function(resp){
            if(resp.error == 'false'){
            	resp.data.unshift({id:'-1',name:'全部'});
                $scope.branchsList = resp.data;
                $scope.selectedBranch = $scope.branchsList[0];
            }
        })
    }


    /**
     * 查询公司信息
     */
    function queryCompanys(){
        CompanyService.get({},function(resp){
            if(resp.error == 'false'){
            	resp.data.unshift({id:'-1',name:'全部'});
                $scope.companys = resp.data;
                $scope.selectedCompany = $scope.companys[0];
            }
        })
    }
    
    /**
     * 初始化分页数组
     * @param n
     * @returns {Array}
     */
   $scope.initNumber = function(n){
        var list = new Array();
        for (var index = 1;index<=10&&index<=n;index++){
            list.push(index);
        }
        $scope.paginationBars=list;
    }
    
    /**
     * 获取分页导航条
     */
    function changeNumber(){
    	 var list = new Array();
         //如果当前页码跳到了尾页
         if($scope.currentPage==$scope.totalPage){
        	 var $i=$scope.totalPage-9;
        	 var $index=$i<0?1:$i;
        	 for (var index = $index;index<=$scope.totalPage;index++){
                 list.push(index);
             }
        	 $scope.paginationBars=list;
        	 
         }
         //首页
         else if($scope.currentPage==1){
        	 $scope.initNumber($scope.totalPage);
         } else{ //如果当前页码不在尾页
        	 var len=$scope.paginationBars.length;
        	 var lastIndex=$scope.paginationBars[len-1];
        	 if($scope.currentPage< $scope.paginationBars[0]){
        		 $scope.paginationBars.splice(0,0,$scope.paginationBars[0]-1);
        		 $scope.paginationBars.splice(len,1); 
        	 }
        	 else if($scope.currentPage>lastIndex &&$scope.currentPage<$scope.totalPage){
        		 $scope.paginationBars.splice(0,1); 
        		 $scope.paginationBars.splice(len-1,0,lastIndex+1);
        	 }
         }
    }
    
    $scope.queryIndex=function($index){
    	if(($index<=0||$index>$scope.totalPage)&&$scope.totalPage>0)
    		return;
    	 $scope.currentPage=$index;
    	 queryPUBStudentAccountChange();
    }

    $scope.queryInfo();
    queryPos();
    queryCompanyAccount();
    queryProduct();
    queryCompanys();
    queryBranchs();
}