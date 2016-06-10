Behaviour.register({
	'#newButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/editMemberRecordType";
		}
	},
	
	'img.details': function(img) {
		setPointer(img);
		img.onclick = function() {
			self.location = pathPrefix + "/editMemberRecordType?memberRecordTypeId=" + img.getAttribute("memberRecordTypeId");
		}
	},
	
	'img.remove': function(img) {
		setPointer(img);
		img.onclick = function() {
			if (confirm(removeConfirmation)) {
				self.location = pathPrefix + "/removeMemberRecordType?memberRecordTypeId=" + img.getAttribute("memberRecordTypeId");
			}
		}
	}
});