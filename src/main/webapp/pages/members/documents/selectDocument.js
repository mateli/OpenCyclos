Behaviour.register({

	'img.dynamicPreview': function(img) {
		setPointer(img);
		img.onclick = function() {
			var hasFormPage = "true" == img.getAttribute("hasFormPage");
			var url = pathPrefix + "/viewDynamicDocument?memberId=" + memberId + "&documentId=" + img.getAttribute("documentId");
			if (hasFormPage) {
				self.location = url;
			} else {
				printResults(null, url);
			}
		} 
	},

	'img.memberPreview': function(img) {
		setPointer(img);
		img.onclick = function() {
			var url = pathPrefix + "/viewMemberDocument?documentId=" + img.getAttribute("documentId");
			window.open(url, 'pop', 'scrollbars=yes,resizable,width=620,height=500,top=10,left=10');
		}
	},
		
	'img.staticPreview': function(img) {
		setPointer(img);
		img.onclick = function() {
			var url = pathPrefix + "/viewStaticDocument?documentId=" + img.getAttribute("documentId");
			window.open(url, 'pop', 'scrollbars=yes,resizable,width=620,height=500,top=10,left=10');
		}
	},

	'img.staticBrokerPreview': function(img) {
		setPointer(img);
		img.onclick = function() {
			var url = pathPrefix + "/viewStaticDocumentAsBroker?documentId=" + img.getAttribute("documentId");
			window.open(url, 'pop', 'scrollbars=yes,resizable,width=620,height=500,top=10,left=10');
		}
	},
	
	'img.edit': function(img) {
		setPointer(img);
		img.onclick = function() {
			self.location = pathPrefix + "/editMemberDocument?documentId=" + img.getAttribute("documentId");
		}
	},
	
	'img.remove': function(img) {
		setPointer(img);
		img.onclick = function() {
			if (confirm(removeConfirmationMessage)) {
				self.location = pathPrefix + "/removeDocument?documentId=" + img.getAttribute("documentId");
			}
		}
	},

	'#newMemberButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/editMemberDocument?memberId=" + memberId;
		}
	},

	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/profile?memberId=" + memberId;
		}
	}
	
});