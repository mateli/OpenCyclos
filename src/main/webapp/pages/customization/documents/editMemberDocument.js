Behaviour.register({
	
	'#currentFileLink': function(a) {
		setPointer(a);
		a.onclick = function() {
			var url = pathPrefix + "/viewMemberDocument?documentId=" + a.getAttribute("documentId");
			window.open(url, 'pop', 'scrollbars=yes,resizable,width=620,height=500,top=10,left=10');
		}
	},
	
	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/selectDocument?memberId=" + getValue("document(member)");
		}
	},
		
	'#descriptionText': function(textarea) {
		new SizeLimit(textarea, 1000);
	},
	
	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	},
		
	'#modifyButton': function(button) {
		button.onclick = function() {
			enableFormFields.apply(button.form);
			getObject('document(name)').focus();
		}
	}
});

Event.observe(self, "load", function() {
	if (isEmpty(getValue("document(id)"))) {
		enableFormForInsert();
	} else {
	    getObject("upload").disabled = true;
	}
});
