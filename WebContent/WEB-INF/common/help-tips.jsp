<%@ page language="java" pageEncoding="UTF-8"%>
<%-- 显示操作消息--%>
<div class="box_shrink">
		<div class="box_shrink_left"><a style="cursor: pointer;" class="dot_tip">${helpTips.title }</a></div>
		<div class="box_shrink_left1" style="display:none;">
			<div id="top_tips_ins_psn_folder">
				${helpTips.content }
			</div>
		</div>
		<div class="box_shrink_right">
		<a style="cursor: pointer;" class="box_shrink_left_down"><img src="${res}/images/icon_nav_arrowdown.gif" border="0" align="absmiddle" /></a>
		<a style="cursor: pointer;" class="box_shrink_left_up" style="display:none;" ><img src="${res}/images/icon_nav_arrowup.gif" border="0" align="absmiddle" /></a>
		</div>
</div>