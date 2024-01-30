package crimera.patches.twitter.view

import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.data.ResourceContext
import app.revanced.patcher.extensions.InstructionExtensions.addInstructions
import app.revanced.patcher.extensions.InstructionExtensions.getInstructions
import app.revanced.patcher.extensions.InstructionExtensions.removeInstructions
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patcher.patch.PatchException
import app.revanced.patcher.patch.ResourcePatch
import app.revanced.patcher.patch.annotation.CompatiblePackage
import app.revanced.patcher.patch.annotation.Patch
import crimera.patches.twitter.view.fingerprints.RemoveViewCountPatchFingerprint

@Patch(
    name = "Remove view count",
    description = "Removes the view count from the bottom of tweets",
    compatiblePackages = [CompatiblePackage("com.twitter.android")]
)
object RemoveViewCountPatch: BytecodePatch(
    setOf(RemoveViewCountPatchFingerprint)
) {
    override fun execute(context: BytecodeContext) {
        val result = RemoveViewCountPatchFingerprint.result
            ?: throw PatchException("Fingerprint not found")

        val method = result.mutableMethod
        val instructions = method.getInstructions()

        method.removeInstructions(0, instructions.count())

        method.addInstructions("""
            const/4 v0, 0x0
            return v0
        """.trimIndent())
    }

}