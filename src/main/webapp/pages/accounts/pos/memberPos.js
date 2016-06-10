function confirmPosAssignement(params) {
	if (params.returnValue) {
		try {
			var element = params.xml.getElementsByTagName("validationMessage");
			var message = element.item(0).firstChild.data;
			if(message == 'POS_NOT_FOUND'){
				var element2 = params.xml.getElementsByTagName("confirmationMessage");
				var confirmation = element2.item(0).firstChild.data;
				if (!confirm(confirmation)) {					
					return false;
				}
			} else if (message == 'POS_UNAVAILABLE'){
				var element2 = params.xml.getElementsByTagName("errorMessage");
				var errorMessage = element2.item(0).firstChild.data;
				alert(errorMessage);
				return false;
			} else if (message == 'POS_AVAILABLE'){
				return true;
			}
			
		} catch (exception) {
			return false;
		}
	}
}

Behaviour.register({
	'img.details': function(img) {
		setPointer(img);
		img.onclick = function() {
			self.location = pathPrefix + "/editPos?memberId=" + memberId + "&id=" + img.getAttribute("posMainId");
		}
	},
	'#backButton':function(button){
		button.onclick = function(){
			self.location = pathPrefix + "/profile?memberId=" + memberId;
		}
	},
	
	'form': function(form) {
		form.onsubmit = function() {			
			return requestValidation(form, null, confirmPosAssignement);
		}
	}
});