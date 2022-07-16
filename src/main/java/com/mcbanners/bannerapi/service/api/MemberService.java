package com.mcbanners.bannerapi.service.api;

import com.mcbanners.bannerapi.obj.generic.Member;
import com.mcbanners.bannerapi.service.ServiceBackend;

public interface MemberService {

    /**
     * Gets a member by their ID on the providing API service
     *
     * @param memberId the member ID
     * @param backend the service backend to query
     * @return the generic Member object representing the member
     */
    Member getMember(final int memberId, final ServiceBackend backend);
}
