Behaviour.register({
	'#newDynamicButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/editDynamicDocument";
		}
	},
	
	'#newStaticButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/editStaticDocument";
		}
	},
	
	'img.dynamicDocumentDetails': function(img) {
		setPointer(img);
		img.onclick = function() {
			self.location = pathPrefix + "/editDynamicDocument?documentId=" + img.getAttribute("documentId");
		}
	},
	
	'img.staticDocumentDetails': function(img) {
		setPointer(img);
		img.onclick = function() {
			self.location = pathPrefix + "/editStaticDocument?documentId=" + img.getAttribute("documentId");
		}
	},
	
	'img.dynamicPreview': function(img) {
		setPointer(img);
		img.onclick = function() {
			var url = pathPrefix + "/previewDynamicDocument?documentId=" + img.getAttribute("documentId");
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
	
	'img.remove': function(img) {
		setPointer(img);
		img.onclick = function() {
			if (confirm(removeConfirmationMessage)) {
				self.location = pathPrefix + "/removeDocument?documentId=" + img.getAttribute("documentId");
			}
		}
	}
});