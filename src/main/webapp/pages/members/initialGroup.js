Behaviour.register({
	'#backButton': function(button) {
		button.onclick = function() {
			history.back();
		}
	},
	
	'a.group': function(a) {
		setPointer(a);
		a.onclick = function() {
			var groupId = a.getAttribute("groupId");
			var url = context + "/do/" + (isPublic ? 'doPublicCreateMember' : 'member/doCreateMember');
			self.location = url + "?groupId=" + groupId;
		}
	}	
});