function isInsert() {
 var id = parseInt(getValue("pos(id)"));
 return (isNaN(id) || id == 0);
} 

function hideAll(){
	var tableDiv = $('tableDiv');
	var pinDiv1 = $('pinDiv1');
	var pinDiv2 = $('pinDiv2');
	var submitDiv = $('submitDiv');
	var assignDiv = $('assignDiv');
	
	submitDiv['hide']();
	pinDiv1['hide']();
	pinDiv2['hide']();
	tableDiv['hide']();	
	assignDiv['hide']();	
	
}
function submitForm(form){
	form.action = pathPrefix + "/editPos?operation=" + operation +"&posId=" + getValue("pos(id)")+"&memberId=" + memberId;	
	form.submit();
}

Behaviour.register({
	'form': function(form) {
		var operation = getValue("operation");
		if(operation == ''){
			operation = 'updatePos';
		}
		form.action = pathPrefix + "/editPos?operation=" + operation +"&posId=" + getValue("pos(id)")+"&memberId=" + memberId;
		form.onsubmit = function() {			
			return requestValidation(form);
		}
	},

	'#backButton': function(button) {
		button.onclick = function() {
			if(memberId == 0){
				self.location = pathPrefix + "/searchPos";
			}else{
				self.location = pathPrefix + "/memberPos?memberId=" + memberId;
			}
		}
	},
	'#block': function(button) {
		button.onclick = function() {
			hideAll();
			operation = 'block';
			if(confirm(confirmBlockPos)){
				submitForm(this.form);
			}
			return false;
			
		}
	},
	'#unblock': function(button) {
		button.onclick = function() {
			hideAll();
			operation = 'unblock';
			if(confirm(confirmUnblockPos)){
				submitForm(this.form);
			}
			return false;			
		}
	},
	'#unblockPin': function(button) {
		button.onclick = function() {
			hideAll();
			operation = 'unblockPin';
			if(confirm(confirmUnblockPin)){
				submitForm(this.form);
			}
			return false;			
		}
	},
	'#assign': function(button) {
		button.onclick = function() {
			hideAll();
			operation = 'assign';
			var tableDiv = $('tableDiv');
			var submitDiv = $('submitDiv');
			var assignDiv = $('assignDiv');
			submitDiv['show']();
			tableDiv['show']();	
			assignDiv['show']();
			$('legendId').innerHTML = actionAssign;			
			setFocus("memberUsername");								
		}
	},
	
	'#changePin': function(button) {
		button.onclick = function() {
			hideAll();
			operation = 'changePin';
			
			var tableDiv = $('tableDiv');
			var submitDiv = $('submitDiv');
			var pinDiv1 = $('pinDiv1');
			var pinDiv2 = $('pinDiv2');
			submitDiv['show']();
			tableDiv['show']();	
			pinDiv1['show']();
			pinDiv2['show']();
			setFocus("_pin1");								
		}
	},
	
	'#discard': function(button) {
		button.onclick = function() {
			hideAll();
			operation = 'discard';
			if(confirm(confirmDiscardPos)){
				submitForm(this.form);
			}
			return false;			
		}
	},
	
	'#unassign': function(button) {
		button.onclick = function() {
			hideAll();
			operation = 'unassign';
			if(confirm(confirmUnassignPos)){
				submitForm(this.form);
			}
			return false;			
		}
	},
	
	
	
	'#submitButton': function(button) {
		button.onclick = function() {
			var submit = true;
			var form = this.form;
			switch(operation){
				case 'assign':{
					if(!confirm(confirmAssignPos)){
						submit = false;
					}else{
						if(isEmpty(getValue("memberId"))){
							alert(invalidMember);
							return false;
						}
						form.assignTo.value=getValue("memberId");
					}
					break;
				}
				case 'changePin':{
					if(!confirm(confirmChangePin)){
						submit = false;
					}
					new1 = getValue("_pin1");
					new2 = getValue("_pin2");
					if (new1 != new2) {
						alert(pinNotEqual);
						return false;
					}
					if (new1 == '') {
						alert(pinIsEmpty);
						return false;
					}
				    form.pin.value = new1;
				    delete new1;
				    delete new2;
					break;
				}								
			}
			if(!submit){
				return false;
			}
			submitForm(form);	
		}
	},
	
	'#memberUsername': function(input) {
		var div = $('membersByUsername');
		prepareForMemberAutocomplete(input, div, {paramName:"username",brokered:true}, 'memberId', 'memberUsername', 'memberName');
	},
	
	'#memberName': function(input) {
		var div = $('membersByName');
		prepareForMemberAutocomplete(input, div, {paramName:"name",brokered:true}, 'memberId', 'memberUsername', 'memberName');
	},
	
	'#modifyButton': function(button) {
		button.onclick = function() {
			if(isInsert()){
				enableFormFields.apply(button.form, ["posStatus"]);
				getObject('pos(posId)').focus();
			}else{
				if(hasMemberPos){
					if(isRegularUser){
						enableFormFields.apply(button.form, ["posIdentifier", "posStatus","memberUsername", "memberName", "memberLogin","userName", "memberPosDate", "memberPosStatus", "assign","block","changePin", "discard", "unassign","unblock","_pin1","_pin2","submitButton", "posStatus", "memberPosStatus", "description"]);
						getObject('pos(memberPos).posName').focus();
					}else{
						enableFormFields.apply(button.form, ["posIdentifier", "posStatus","memberUsername", "memberName", "memberLogin","userName", "memberPosDate", "memberPosStatus", "assign","block","changePin", "discard", "unassign","unblock","_pin1","_pin2","submitButton", "posStatus", "memberPosStatus"]);
						getObject('pos(description)').focus();
					}
				}else{
					enableFormFields.apply(button.form, ["posIdentifier", "posStatus", "assign","block","changePin", "discard", "unassign","unblock","_pin1","_pin2","submitButton", "posStatus", "memberPosStatus"]);
					getObject('pos(description)').focus();
				}				
			}
		}
	},
	
	'#newPosButton': function(button) {
		button.onclick = function() {
			operation = 'newPos';
			submitForm(this.form);
		}
	}
});


Event.observe(self, "load", function() {
	if (isInsert()) {
		enableFormForInsert();
	}
});